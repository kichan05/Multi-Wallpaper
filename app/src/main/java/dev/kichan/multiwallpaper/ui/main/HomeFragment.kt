package dev.kichan.multiwallpaper.ui.main

import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.kichan.multiwallpaper.BaseFragment
import dev.kichan.multiwallpaper.ExtraKey
import dev.kichan.multiwallpaper.R
import dev.kichan.multiwallpaper.databinding.FragmentHomeBinding
import dev.kichan.multiwallpaper.model.data.Wallpaper
import dev.kichan.multiwallpaper.model.db.WallpaperDataBase
import dev.kichan.multiwallpaper.ui.SelectWallpaperScreen
import dev.kichan.multiwallpaper.ui.ViewPagerAdapter
import dev.kichan.multiwallpaper.ui.add.AddWallpaperActivity
import dev.kichan.multiwallpaper.ui.remove.RemoveWallpaperActivity
import kotlinx.coroutines.*

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private var wallpaperData: MutableList<Wallpaper> = mutableListOf()
    private var isOptionOpen = false
    val db: WallpaperDataBase by lazy {
        Room.databaseBuilder(
            requireContext(),
            WallpaperDataBase::class.java,
            WallpaperDataBase.dbName
        ).build()
    }

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
            btnMainOpenOption.setOnClickListener(clickOption) // 옵션 열기
            btnMainSetWallpaper.setOnClickListener(clickSetWallpaper) // 배경화면 설정
            btnMainAddWallpaper.setOnClickListener(clickAddWallpaper) // 배경화면 추가
            btnMainDeleteWallpaper.setOnClickListener(clickRemoveWallpaper) // 배경화면 삭제

//            dotHome.attachTo(vpMain)
        }

        closeOption()
        updateData()
        setViewPagerPreView()
    }

    private fun updateData() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = db.wallpaperDao().getAll()
            withContext(Dispatchers.Main) {
                wallpaperData.clear()
                wallpaperData = result as MutableList<Wallpaper>

                if (wallpaperData.size == 0) {
                    binding.layoutMainNull.visibility = View.VISIBLE
                    binding.btnMainSetWallpaper.visibility = View.INVISIBLE
                    return@withContext
                }

                binding.layoutMainNull.visibility = View.INVISIBLE
                binding.btnMainSetWallpaper.visibility = View.VISIBLE

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
        val modalBottomSheet = SelectWallpaperScreen(wallpaperData[wallpaperIndex])
        modalBottomSheet.show(parentFragmentManager, SelectWallpaperScreen.TAG)
    }

    private val clickOption: (View) -> Unit = {
        if (it is FloatingActionButton) {
            if (isOptionOpen) {
                closeOption()
            } else {
                openOption()
            }
        }
    }

    private fun closeOption() = binding.run {
        btnMainDeleteWallpaper.visibility = View.INVISIBLE
        btnMainAddWallpaper.visibility = View.INVISIBLE
        btnMainOpenOption.setImageResource(R.drawable.ic_option)

        isOptionOpen = false
    }

    private fun openOption() = binding.run {
        btnMainDeleteWallpaper.visibility = View.VISIBLE
        btnMainAddWallpaper.visibility = View.VISIBLE
        btnMainOpenOption.setImageResource(R.drawable.ic_close)

        isOptionOpen = true
    }

    private val clickAddWallpaper: (View) -> Unit = {
        getImage.launch("image/*")
    }

    private val clickRemoveWallpaper: (View) -> Unit = {
        val intent = Intent(context, RemoveWallpaperActivity::class.java)
        startActivity(intent)
    }
}