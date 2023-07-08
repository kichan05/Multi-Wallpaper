package dev.kichan.multiwallpaper.ui.main

import android.app.WallpaperManager
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import dev.kichan.multiwallpaper.BaseFragment
import dev.kichan.multiwallpaper.ExtraKey
import dev.kichan.multiwallpaper.R
import dev.kichan.multiwallpaper.databinding.ActivityMainBinding
import dev.kichan.multiwallpaper.databinding.FragmentHomeBinding
import dev.kichan.multiwallpaper.model.data.Wallpaper
import dev.kichan.multiwallpaper.model.db.WallpaperDataBase
import dev.kichan.multiwallpaper.ui.LoadingDialog
import dev.kichan.multiwallpaper.ui.ViewPagerAdapter
import dev.kichan.multiwallpaper.ui.addWallpaper.AddWallpaperActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private var wallpaperData: MutableList<Wallpaper> = mutableListOf()

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it == null) {
            return@registerForActivityResult
        }

        val intent = Intent(activity, AddWallpaperActivity::class.java).apply {
            putExtra(ExtraKey.GET_IMAGE_EXTRA.key, it.toString())
        }

        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()

        binding.run {
            btnMainSetWallpaper.setOnClickListener(clickSetWallpaper)
            btnMainAddWallpaper.setOnClickListener(clickAddWallpaper)
        }

        updateData()
        setViewPagerPreView()
    }

    private fun updateData() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = Room.databaseBuilder(
                requireContext(),
                WallpaperDataBase::class.java,
                WallpaperDataBase.dbName
            ).build()

            val result = db.wallpaperDao().getAll()

            withContext(Dispatchers.Main) {
                wallpaperData.clear()
                wallpaperData.addAll(result)

                if (wallpaperData.size == 0) {
                    return@withContext
                }

                binding.layoutMainNull.visibility = View.GONE

                binding.vpMain.adapter = ViewPagerAdapter(
                    requireActivity(),
                    wallpaperData.map { WallpaperFragment(it) }
                )
            }
        }
    }

    private fun setViewPagerPreView() = binding.vpMain.run {
        clipToPadding = false
        setPageTransformer { page, position ->
            page.translationX = -(40 * position)
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
        }
    }


    private val clickSetWallpaper: (View) -> Unit = { v ->
        val wallpaperIndex = binding.vpMain.currentItem

        WallpaperManager.getInstance(context).setBitmap(wallpaperData[wallpaperIndex].wallpaper)

//        val dialog = LoadingDialog().apply {
//            show(fragmentManager, "AA")
//        }
//        dialog.dismiss()

        val launcher = Intent(Intent.ACTION_MAIN)
        launcher.addCategory(Intent.CATEGORY_HOME)
        startActivity(launcher)
    }

    private val clickAddWallpaper: (View) -> Unit = { v ->
        getImage.launch("image/*")
    }
}