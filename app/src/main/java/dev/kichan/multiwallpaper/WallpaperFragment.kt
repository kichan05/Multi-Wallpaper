package dev.kichan.multiwallpaper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dev.kichan.multiwallpaper.databinding.FragmentWallpaperBinding

class WallpaperFragment(val wallpaper: Wallpaper) : Fragment() {
    lateinit var binding : FragmentWallpaperBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallpaper, container, false)

        binding.imgMainWallpaper.run {
            setImageBitmap(wallpaper.wallpaper)
            clipToOutline = true

            val previewSize = Util.getPreViewImageSize(requireActivity(), 90)
            layoutParams.width = previewSize.x
            layoutParams.height = previewSize.y
        }
        binding.txtMainWallpaperName.text = wallpaper.wallpaperName

        return binding.root
    }

//    companion object{
//        private const val KEY_ONBOARD = "wallpaper"
//
//        @JvmStatic
//        fun newInstance(wallpaper: Wallpaper) = WallpaperFragment().apply {
//            arguments = Bundle().apply {
//                putSerializable(KEY_ONBOARD, wallpaper)
//            }
//        }
//    }
}