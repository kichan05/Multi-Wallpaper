package dev.kichan.multiwallpaper.ui.select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.kichan.multiwallpaper.databinding.RowWallpaperGalleryBinding
import dev.kichan.multiwallpaper.model.data.Wallpaper

class WallpaperGalleryAdapter(val items : List<Wallpaper>, val itemClickListener: (Int) -> Unit) : RecyclerView.Adapter<WallpaperGalleryViewHolder>() {
    var selectItemIndex : Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperGalleryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = RowWallpaperGalleryBinding.inflate(layoutInflater, parent, false)
        return WallpaperGalleryViewHolder(row)
    }

    override fun onBindViewHolder(holder: WallpaperGalleryViewHolder, position: Int) {
        holder.row.root.setOnClickListener {
            if(selectItemIndex != null){
                itemClickListener(selectItemIndex!!)
            }

            if(position == selectItemIndex){
                selectItemIndex = null
            }
            else{
                selectItemIndex = position
                holder.row.ckGalleryItem.isChecked = true
            }
        }
        holder.onBind(items[position])
    }

    fun getSelectItem() : Wallpaper = items[selectItemIndex!!]

    override fun getItemCount(): Int = items.size
}