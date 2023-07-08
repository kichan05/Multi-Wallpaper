package dev.kichan.multiwallpaper.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dev.kichan.multiwallpaper.BaseActivity
import dev.kichan.multiwallpaper.R
import dev.kichan.multiwallpaper.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = findNavController(R.id.fragment_main)
        binding.bnvMain.setupWithNavController(navController)
    }
}