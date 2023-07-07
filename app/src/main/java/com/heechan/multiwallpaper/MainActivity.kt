package com.heechan.multiwallpaper

import android.app.WallpaperManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.heechan.multiwallpaper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main).apply {
            vpMain.adapter = ViewPagerAdapter(this@MainActivity, tempData.map { WallpaperFragment(it) })
            btnMainSetWallpaper.setOnClickListener(clickSetWallpaper)
            btnMainAddWallpaper.setOnClickListener(clickAddWallpaper)
        }
    }

    private val clickSetWallpaper: (View) -> Unit = { v ->
        val wallpaperIndex = binding.vpMain.currentItem
        val a = tempData[wallpaperIndex]

        WallpaperManager.getInstance(this).setResource(a.imageDrawable!!)
    }

    private val clickAddWallpaper: (View) -> Unit = { v ->
        val intent = Intent(this, AddWallpaperActivity::class.java)
        startActivity(intent)
    }

    companion object {
        val tempData: List<Wallpaper> = listOf(
            Wallpaper(wallpaperName = "봇치", imageDrawable = R.drawable.hitori),
            Wallpaper(wallpaperName = "키타", imageDrawable = R.drawable.kita),
            Wallpaper(wallpaperName = "료", imageDrawable = R.drawable.ryo),
            Wallpaper(wallpaperName = "니지카", imageDrawable = R.drawable.nijika),
        )
    }
}