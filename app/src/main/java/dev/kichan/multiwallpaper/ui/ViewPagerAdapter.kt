package dev.kichan.multiwallpaper.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.kichan.multiwallpaper.ui.main.WallpaperFragment

class ViewPagerAdapter(
    fm: FragmentActivity,
    private val fragmentList: List<WallpaperFragment>) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = fragmentList.size
    override fun createFragment(position: Int): Fragment = fragmentList[position]
}