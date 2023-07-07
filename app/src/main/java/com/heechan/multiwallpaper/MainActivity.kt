package com.heechan.multiwallpaper

import android.app.WallpaperManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.heechan.multiwallpaper.databinding.ActivityMainBinding
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var tempData : List<Wallpaper>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tempData = listOf(
            Wallpaper(wallpaperName = "봇치", imageDrawable = R.drawable.hitori, context = this),
            Wallpaper(wallpaperName = "키타", imageDrawable = R.drawable.kita, context = this),
            Wallpaper(wallpaperName = "료", imageDrawable = R.drawable.ryo, context = this),
            Wallpaper(wallpaperName = "니지카", imageDrawable = R.drawable.nijika, context = this),
        )

        binding = DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main).apply {
            vpMain.adapter = ViewPagerAdapter(this@MainActivity, tempData.map { WallpaperFragment(it) })
            btnMainSetWallpaper.setOnClickListener(clickSetWallpaper)
            btnMainAddWallpaper.setOnClickListener(clickAddWallpaper)
        }
    }

    private val clickSetWallpaper: (View) -> Unit = { v ->
        val wallpaperIndex = binding.vpMain.currentItem

        WallpaperManager.getInstance(this).setBitmap(tempData[wallpaperIndex].wallpaper)
    }

    private val clickAddWallpaper: (View) -> Unit = { v ->
        val intent = Intent(this, AddWallpaperActivity::class.java)
        startActivity(intent)
    }
}