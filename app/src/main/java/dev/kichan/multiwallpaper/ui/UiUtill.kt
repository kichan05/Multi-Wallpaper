package dev.kichan.multiwallpaper.ui

import android.app.Activity
import android.graphics.Point

object UiUtill {
    fun getPreViewImageSize(activity : Activity, padding : Int) : Point {
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size) // or getSize(size)

        val width = size.x - padding * 2
        val height = size.y * width / size.x

        return Point(width, height)
    }
}