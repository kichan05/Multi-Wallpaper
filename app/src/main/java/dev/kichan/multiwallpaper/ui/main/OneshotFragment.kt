package dev.kichan.multiwallpaper.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import dev.kichan.multiwallpaper.BaseFragment
import dev.kichan.multiwallpaper.ExtraKey
import dev.kichan.multiwallpaper.R
import dev.kichan.multiwallpaper.databinding.FragmentOneshotBinding
import dev.kichan.multiwallpaper.ui.SelectWallpaperActivity

class OneshotFragment : BaseFragment<FragmentOneshotBinding>(R.layout.fragment_oneshot) {
    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val selectWallpaperId =
                    result.data!!.getSerializableExtra(ExtraKey.SELECT_WALLPAPER.key)
                        ?: return@registerForActivityResult

                Log.d("Result", selectWallpaperId.toString())
            }
        }

    override fun onStart() {
        super.onStart()
        binding.run {
            btnOneShotGetWallpaper.setOnClickListener {
                val intent = Intent(context, SelectWallpaperActivity::class.java)
                startForResult.launch(intent)
            }
        }
    }
}