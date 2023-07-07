package com.heechan.multiwallpaper

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.heechan.multiwallpaper.databinding.ActivityAddWallpaperBinding

class AddWallpaperActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddWallpaperBinding

    var count = 0;

    val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if(it == null){
            return@registerForActivityResult
        }

        binding.imgAddWallpaperPreview.setImageURI(it)

//        val inputStream = contentResolver.openInputStream(it) ?: return@registerForActivityResult
//        val bitmap = BitmapFactory.decodeStream(inputStream)
//        inputStream.close()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_wallpaper)
    }

    override fun onStart() {
        super.onStart()
        if(count == 0){
            getImage.launch("image/*")
            count += 1
        }
    }

}