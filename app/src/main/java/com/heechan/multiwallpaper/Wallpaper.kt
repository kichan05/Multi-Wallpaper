package com.heechan.multiwallpaper

import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes

data class Wallpaper(
    val wallpaperName : String,
    @DrawableRes val imageDrawable : Int,
)
