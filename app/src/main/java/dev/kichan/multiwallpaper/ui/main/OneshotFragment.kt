package dev.kichan.multiwallpaper.ui.main

import android.app.Activity
import android.app.WallpaperManager
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import dev.kichan.multiwallpaper.BaseFragment
import dev.kichan.multiwallpaper.ExtraKey
import dev.kichan.multiwallpaper.R
import dev.kichan.multiwallpaper.databinding.FragmentOneshotBinding
import dev.kichan.multiwallpaper.databinding.ViewOneShotBinding
import dev.kichan.multiwallpaper.model.data.Wallpaper
import dev.kichan.multiwallpaper.model.db.WallpaperDBDao
import dev.kichan.multiwallpaper.model.db.WallpaperDataBase
import dev.kichan.multiwallpaper.ui.LoadingDialog
import dev.kichan.multiwallpaper.ui.select.SelectWallpaperActivity
import kotlinx.coroutines.*

class OneshotFragment : BaseFragment<FragmentOneshotBinding>(R.layout.fragment_oneshot) {
    // todo : 정말 거짓말 안하고 내가 작성한 코드중에서 제일 더럽다. 리팩토링을 하자
//
//    private val db: WallpaperDBDao by lazy {
//        Room.databaseBuilder(
//            requireContext(), WallpaperDataBase::class.java, WallpaperDataBase.dbName
//        ).build().wallpaperDao()
//    }
//
//    private val startForResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            val selectWallpaperId = it.data?.getIntExtra(ExtraKey.SELECT_WALLPAPER.key, -1)
//                ?: return@registerForActivityResult
//
//            if (it.resultCode != Activity.RESULT_OK) return@registerForActivityResult
//            if (selectWallpaperId == -1) return@registerForActivityResult
//
//            saveOneshot(selectWallpaperId, choiceOneshotIndex!!)
//            choiceOneshotIndex = null
//        }
//
//    var choiceOneshotIndex: Int? = null
//    private var oneshotList = listOf<Wallpaper>()
//    private lateinit var oneShowView: List<ViewOneShotBinding>
//
//    override fun onStart() {
//        super.onStart()
//        update()
//        drawUi()
//    }
//
//    private fun drawUi() {
//        val layoutInflater = LayoutInflater.from(context)
//        oneShowView = (1..4).map { index ->
//            val row = ViewOneShotBinding.inflate(
//                layoutInflater, binding.layoutOneShot, false
//            ).apply {
//                imgOneShotPreView.clipToOutline = true
//                txtOneShotTitle.text = "${index}번 원샷"
//
//                btnOneShotSetOneShot.setOnClickListener { setOneshot(index) }
//                btnOneShotSetWallpaper.setOnClickListener { setWallpaper(index) }
//            }
//
//            binding.layoutOneShot.addView(row.root)
//
//            row
//        }
//    }
//
//    private fun update() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = db.getOneShot()
//            withContext(Dispatchers.Main) {
//                oneshotList = result
//
//                (1..4).forEach { index ->
//                    val wallpaper = getOneShotWallpaperIndex(index)
//                    if(wallpaper == null){
//
//                    }
//                    else {
//
//                    }
//                }
//            }
//        }
//    }
//
//    private fun getOneShotWallpaperIndex(index: Int): Wallpaper? {
//        return if (oneshotList.any { it.oneShot == index }) {
//            oneshotList.filter { it.oneShot == index }[0]
//        } else {
//            null
//        }
//
//    }
//
//    private fun setWallpaper(wallpaperIndex: Int) {
//        val wallpaper = getOneShotWallpaperIndex(wallpaperIndex)!!
//
//        CoroutineScope(Dispatchers.Main).launch {
//            val dialog = LoadingDialog()
//            dialog.show(parentFragmentManager, "Loading")
//
//            WallpaperManager.getInstance(context).setBitmap(wallpaper.wallpaper)
//
//            delay(500)
//
//            dialog.dismiss()
//            Snackbar.make(binding.root, "배경 설정 완료", Snackbar.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun setOneshot(oneshotIndex: Int) {
//        choiceOneshotIndex = oneshotIndex
//
//        val intent = Intent(context, SelectWallpaperActivity::class.java)
//        startForResult.launch(intent)
//    }
//
//    private fun saveOneshot(wallpaperIndex: Int, oneshotIndex: Int) {
//        val dialog = LoadingDialog()
//        dialog.show(parentFragmentManager, "Loading")
//
//        CoroutineScope(Dispatchers.IO).launch {
//            val wallpaper = db.getWallpaper(wallpaperIndex)?.apply {
//                oneShot = oneshotIndex
//            } ?: return@launch
//
//            db.update(wallpaper)
//            update()
//        }
//
//        oneshotList.filter {
//            it.oneShot == oneshotIndex
//        }.map {
//            it.copy(oneShot = null)
//        }.forEach {
//            CoroutineScope(Dispatchers.IO).launch {
//                db.update(it)
//            }
//        }
//    }
}