package dev.kichan.multiwallpaper.ui

import androidx.recyclerview.widget.RecyclerView
import dev.kichan.multiwallpaper.databinding.RowWallpaperGalleryBinding
import dev.kichan.multiwallpaper.model.data.Wallpaper

class WallpaperGalleryViewHolder(val row : RowWallpaperGalleryBinding) : RecyclerView.ViewHolder(row.root){
    fun onBind(wallpaper : Wallpaper) {
        row.imgGalleryItem.setImageBitmap(wallpaper.wallpaper)
    }
}