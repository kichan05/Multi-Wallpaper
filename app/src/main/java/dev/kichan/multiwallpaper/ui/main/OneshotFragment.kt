package dev.kichan.multiwallpaper.ui.main

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.room.Room
import dev.kichan.multiwallpaper.BaseFragment
import dev.kichan.multiwallpaper.ExtraKey
import dev.kichan.multiwallpaper.R
import dev.kichan.multiwallpaper.databinding.FragmentOneshotBinding
import dev.kichan.multiwallpaper.model.data.Wallpaper
import dev.kichan.multiwallpaper.model.db.WallpaperDataBase
import dev.kichan.multiwallpaper.ui.select.SelectWallpaperActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OneshotFragment : BaseFragment<FragmentOneshotBinding>(R.layout.fragment_oneshot) {
    private lateinit var db: WallpaperDataBase
    private var oneshotList = listOf<Wallpaper>()

    var choiceOneshotIndex: Int? = null

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val selectWallpaperId =
                    result.data!!.getIntExtra(ExtraKey.SELECT_WALLPAPER.key, -1)

                if (selectWallpaperId == -1) {
                    return@registerForActivityResult
                }
                Log.d("result", "선택 : $selectWallpaperId")
                saveOneshot(selectWallpaperId, choiceOneshotIndex!!)
            }
        }

    override fun onStart() {
        super.onStart()
        db = Room.databaseBuilder(
            requireContext(),
            WallpaperDataBase::class.java,
            WallpaperDataBase.dbName
        ).build()

        update()

        binding.run {
            btnOneShotOneShot1.setOnClickListener { setOneshot(1) }
            btnOneShotOneShot2.setOnClickListener { setOneshot(2) }
            btnOneShotOneShot3.setOnClickListener { setOneshot(3) }
            btnOneShotOneShot4.setOnClickListener { setOneshot(4) }
        }
    }

    private fun update() {
        Log.d("Result", "실행")
        CoroutineScope(Dispatchers.IO).launch {
            val result = db.wallpaperDao().getOneShot()
            result.forEach {
                Log.d("Result", it.toString())
            }

            withContext(Dispatchers.Main) {
                oneshotList = result
            }
        }
    }

    private fun setOneshot(oneshotIndex: Int) {
        choiceOneshotIndex = oneshotIndex

        val intent = Intent(context, SelectWallpaperActivity::class.java)
        startForResult.launch(intent)
    }

    private fun saveOneshot(wallpaperIndex: Int, oneshotIndex: Int) {
//        val dialog = LoadingDialog().apply {
//            show(childFragmentManager, "Loading")
//        }

        CoroutineScope(Dispatchers.IO).launch {
            val wallpaper = db.wallpaperDao().getWallpaper(wallpaperIndex)?.apply {
                oneShot = oneshotIndex
            } ?: return@launch

            db.wallpaperDao().update(wallpaper)
            update()
        }

        oneshotList.filter {
            it.oneShot == oneshotIndex
        }.map {
            it.copy(oneShot = null)
        }.forEach {
            CoroutineScope(Dispatchers.IO).launch {
                db.wallpaperDao().update(it)
            }
        }
    }
}