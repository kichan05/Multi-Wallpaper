package com.heechan.multiwallpaper

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

        binding.apply {
            imgMainWallpaper.setImageResource(wallpaper.imageDrawable)
            txtMainWallpaperName.text = wallpaper.wallpaperName
        }

        return binding.root
    }
}