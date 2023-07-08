package dev.kichan.multiwallpaper.ui.select

import androidx.recyclerview.widget.RecyclerView
import dev.kichan.multiwallpaper.databinding.RowWallpaperGalleryBinding
import dev.kichan.multiwallpaper.model.data.Wallpaper

class WallpaperGalleryViewHolder(val row: RowWallpaperGalleryBinding, private val imageSize: Int) :
    RecyclerView.ViewHolder(row.root) {
    fun onBind(wallpaper: Wallpaper) {
        row.imgGalleryItem.setImageBitmap(wallpaper.wallpaper)
        row.imgGalleryItem.layoutParams.width = imageSize
        row.imgGalleryItem.layoutParams.height = imageSize
    }
}