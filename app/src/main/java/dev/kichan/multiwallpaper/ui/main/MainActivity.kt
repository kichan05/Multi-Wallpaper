package dev.kichan.multiwallpaper.ui.main

import android.app.WallpaperManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import dev.kichan.multiwallpaper.ExtraKey
import dev.kichan.multiwallpaper.R
import dev.kichan.multiwallpaper.ui.LoadingDialog
import dev.kichan.multiwallpaper.databinding.ActivityMainBinding
import dev.kichan.multiwallpaper.model.db.WallpaperDataBase
import dev.kichan.multiwallpaper.ui.ViewPagerAdapter
import dev.kichan.multiwallpaper.ui.addWallpaper.AddWallpaperActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}