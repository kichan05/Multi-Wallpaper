package dev.kichan.multiwallpaper.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dev.kichan.multiwallpaper.R
import dev.kichan.multiwallpaper.ui.UiUtil
import dev.kichan.multiwallpaper.databinding.FragmentWallpaperBinding
import dev.kichan.multiwallpaper.model.data.Wallpaper
import java.text.Format
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import android.view.View

class WallpaperFragment(val wallpaper: Wallpaper) : Fragment() {
    lateinit var binding: FragmentWallpaperBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallpaper, container, false)

        binding.imgMainWallpaper.run {
            setImageBitmap(wallpaper.wallpaper)
            clipToOutline = true

            val previewSize = UiUtil.getPreViewImageSize(requireActivity(), 90)
            layoutParams.width = previewSize.x
            layoutParams.height = previewSize.y
        }

        Log.e("test", resources.getString(R.string.layout_wallpaper_addDate))

        val dateFormat = wallpaper.getDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        binding.txtMainCreateDate.text = String.format(resources.getString(R.string.layout_wallpaper_addDate), dateFormat)


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