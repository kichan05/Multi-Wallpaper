package dev.kichan.multiwallpaper.ui.select

import android.app.Activity
import android.content.Intent
import android.graphics.Point
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import dev.kichan.multiwallpaper.BaseActivity
import dev.kichan.multiwallpaper.ExtraKey
import dev.kichan.multiwallpaper.R
import dev.kichan.multiwallpaper.databinding.ActivitySelectWallpaperBinding
import dev.kichan.multiwallpaper.model.data.Wallpaper
import dev.kichan.multiwallpaper.model.db.WallpaperDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectWallpaperActivity :
    BaseActivity<ActivitySelectWallpaperBinding>(R.layout.activity_select_wallpaper) {
    private lateinit var galleryAdapter: WallpaperGalleryAdapter
    private var wallpaperData: MutableList<Wallpaper> = mutableListOf()

    override fun onStart() {
        super.onStart()

        binding.run {
            btnSelectWallpaperSelect.setOnClickListener(clickSelectWallpaper)
            listSelectWallpaper.layoutManager = GridLayoutManager(this@SelectWallpaperActivity, 3)
        }

        updateData()
    }

    private val clickGalleryItem: (Int) -> Unit = {
        val holder =
            binding.listSelectWallpaper.findViewHolderForAdapterPosition(it) as WallpaperGalleryViewHolder
        holder.row.ckGalleryItem.isChecked = false
    }

    private val clickSelectWallpaper: (View) -> Unit = {
        if (::galleryAdapter.isInitialized) {
            val selectWallpaperId = galleryAdapter.selectItemIndex
            if (selectWallpaperId != null) {
                val intent = Intent().apply {
                    putExtra(ExtraKey.SELECT_WALLPAPER.key, galleryAdapter.getSelectItem().id)
                }

                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun updateData() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = Room.databaseBuilder(
                this@SelectWallpaperActivity,
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

                val display = windowManager.defaultDisplay
                val size = Point()
                display.getSize(size)

                galleryAdapter = WallpaperGalleryAdapter(wallpaperData, clickGalleryItem, size.x / 3)
                binding.listSelectWallpaper.adapter = galleryAdapter
            }
        }
    }
}

