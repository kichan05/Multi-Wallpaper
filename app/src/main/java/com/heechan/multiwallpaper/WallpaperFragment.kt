package com.heechan.multiwallpaper

import android.graphics.Point
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.heechan.multiwallpaper.databinding.FragmentWallpaperBinding

class WallpaperFragment(
    val wallpaper : Wallpaper
) : Fragment() {
    lateinit var binding : FragmentWallpaperBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallpaper, container, false)

        binding.imgMainWallpaper.run {
            setImageBitmap(wallpaper.wallpaper)
            clipToOutline = true

            val previewSize = getPreViewImageSize()
            layoutParams.width = previewSize.x
            layoutParams.height = previewSize.y
        }
        binding.txtMainWallpaperName.text = wallpaper.wallpaperName

        return binding.root
    }

    private fun getPreViewImageSize() : Point {
        val display = requireActivity().windowManager.defaultDisplay
        val size = Point()
        display.getSize(size) // or getSize(size)

        val width = size.x - 90 * 2
        val height = size.y * width / size.x

        return Point(width, height)
    }
}