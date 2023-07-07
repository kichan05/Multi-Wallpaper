package com.heechan.multiwallpaper

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.heechan.multiwallpaper.databinding.ActivityMainBinding
import com.heechan.multiwallpaper.model.db.WallpaperDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var wallpaperData : MutableList<Wallpaper> = mutableListOf()

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it == null) {
            return@registerForActivityResult
        }

        val intent = Intent(this, AddWallpaperActivity::class.java).apply {
            putExtra(ExtraKey.GET_IMAGE_EXTRA.key, it.toString())
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main).apply {
            btnMainSetWallpaper.setOnClickListener(clickSetWallpaper)
            btnMainAddWallpaper.setOnClickListener(clickAddWallpaper)
        }

        updateData()
    }

    override fun onRestart() {
        super.onRestart()
        updateData()
    }

    private fun updateData() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = Room.databaseBuilder(
                applicationContext,
                WallpaperDataBase::class.java,
                WallpaperDataBase.dbName
            ).build()

            val result = db.wallpaperDao().getAll()

            withContext(Dispatchers.Main){
                wallpaperData.clear()
                wallpaperData.addAll(result)

                if(wallpaperData.size == 0){
                    return@withContext
                }

                binding.vpMain.adapter = ViewPagerAdapter(this@MainActivity, wallpaperData.map { WallpaperFragment(it) })
            }
        }
    }

    private val clickSetWallpaper: (View) -> Unit = { v ->
        val wallpaperIndex = binding.vpMain.currentItem

        WallpaperManager.getInstance(this).setBitmap(wallpaperData[wallpaperIndex].wallpaper)
    }

    private val clickAddWallpaper: (View) -> Unit = { v ->
        getImage.launch("image/*")
    }
}