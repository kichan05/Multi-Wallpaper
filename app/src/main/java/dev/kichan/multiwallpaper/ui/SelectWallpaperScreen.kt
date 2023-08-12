package dev.kichan.multiwallpaper.ui

import android.app.WallpaperManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dev.kichan.multiwallpaper.R
import dev.kichan.multiwallpaper.databinding.LayoutSelectScreenBinding
import dev.kichan.multiwallpaper.model.data.Wallpaper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SelectWallpaperScreen(val wallpaper : Wallpaper) : BottomSheetDialogFragment() {
    lateinit var dialog : LoadingDialog
    lateinit var binding : LayoutSelectScreenBinding

    enum class Screen {
        HOME, LOCK, HOME_AND_LOCK
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_select_screen, container, false)

        dialog = LoadingDialog()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtSelectScreenHome.setOnClickListener { changeWallpaper(Screen.HOME) }
        binding.txtSelectScreenLock.setOnClickListener { changeWallpaper(Screen.LOCK) }
        binding.txtSelectScreenHomeAndlock.setOnClickListener { changeWallpaper(Screen.HOME_AND_LOCK) }
    }

    private fun changeWallpaper(which : Screen) {
        dialog.show(parentFragmentManager, LoadingDialog.TAG)

        CoroutineScope(Dispatchers.Main).launch {
            delay(1)

            if(which == Screen.HOME || which == Screen.HOME_AND_LOCK)
                WallpaperManager.getInstance(context).setBitmap(wallpaper.wallpaper, null, false, WallpaperManager.FLAG_SYSTEM)

            if(which == Screen.LOCK || which == Screen.HOME_AND_LOCK)
                WallpaperManager.getInstance(context).setBitmap(wallpaper.wallpaper, null, false, WallpaperManager.FLAG_LOCK)

            dialog.dismiss()

            this@SelectWallpaperScreen.dismiss()
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}