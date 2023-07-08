package dev.kichan.multiwallpaper.ui.remove

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.room.Room
import dev.kichan.multiwallpaper.BaseActivity
import dev.kichan.multiwallpaper.ExtraKey
import dev.kichan.multiwallpaper.R
import dev.kichan.multiwallpaper.databinding.ActivityRemoveWallpaperBinding
import dev.kichan.multiwallpaper.model.data.Wallpaper
import dev.kichan.multiwallpaper.model.db.WallpaperDBDao
import dev.kichan.multiwallpaper.model.db.WallpaperDataBase
import dev.kichan.multiwallpaper.ui.LoadingDialog
import dev.kichan.multiwallpaper.ui.UiUtil
import dev.kichan.multiwallpaper.ui.select.SelectWallpaperActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RemoveWallpaperActivity :
    BaseActivity<ActivityRemoveWallpaperBinding>(R.layout.activity_remove_wallpaper) {
    private val db : WallpaperDBDao by lazy{
        Room.databaseBuilder(
            this,
            WallpaperDataBase::class.java,
            WallpaperDataBase.dbName
        ).build().wallpaperDao()
    }
    lateinit var selectWallpaper : Wallpaper

    private val selectWallpaperLaunch =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode != Activity.RESULT_OK || result.data == null) {
                finish()
                return@registerForActivityResult
            }

            val selectWallpaperId = result.data!!.getIntExtra(ExtraKey.SELECT_WALLPAPER.key, -1)
            if (selectWallpaperId == -1) {
                finish()
                return@registerForActivityResult
            }

            CoroutineScope(Dispatchers.IO).launch {
                selectWallpaper = db.getWallpaper(selectWallpaperId) ?: return@launch

                withContext(Dispatchers.Main) {
                    binding.imgRemoveWallpaperPreview.setImageBitmap(selectWallpaper.wallpaper)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.run {
            tbRemoveWallpaper.setNavigationOnClickListener { finish() }
            btnRemoveWallpaperRemove.setOnClickListener(clickRemoveButton)

            imgRemoveWallpaperPreview.clipToOutline = true
            val previewSize = UiUtil.getPreViewImageSize(this@RemoveWallpaperActivity, 80)
            imgRemoveWallpaperPreview.layoutParams.width = previewSize.x
            imgRemoveWallpaperPreview.layoutParams.height = previewSize.y
        }

        val intent = Intent(this, SelectWallpaperActivity::class.java)
        selectWallpaperLaunch.launch(intent)
    }

    private val clickRemoveButton : (View) -> Unit = {
        val dialog = LoadingDialog().apply {
            show(supportFragmentManager, "Loading")
        }

        CoroutineScope(Dispatchers.IO).launch {
            db.delete(selectWallpaper)
            withContext(Dispatchers.Main) {
                finish()
            }
        }
    }
}