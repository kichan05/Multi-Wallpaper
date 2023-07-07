package com.heechan.multiwallpaper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.room.*

@Entity
data class Wallpaper(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("wallpaper_name") val wallpaperName: String,
    val wallpaper: Bitmap? = null
) {

    constructor(wallpaperName: String, @DrawableRes imageDrawable: Int, context: Context) : this(
        wallpaperName =  wallpaperName, wallpaper = BitmapFactory.decodeResource(context.resources, imageDrawable)!!
    )
}