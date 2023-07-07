package com.heechan.multiwallpaper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.heechan.multiwallpaper.databinding.ActivityAddWallpaperBinding
import com.heechan.multiwallpaper.model.db.WallpaperDataBase
import kotlinx.coroutines.*
import java.io.InputStream

class AddWallpaperActivity : AppCompatActivity() {
    private lateinit var db: WallpaperDataBase
    private lateinit var binding: ActivityAddWallpaperBinding
    var selectWallpaper: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityAddWallpaperBinding?>(
            this,
            R.layout.activity_add_wallpaper
        ).apply {
            btnAddWallpaperSave.setOnClickListener(clickSaveWallpaper)
        }

        db = Room.databaseBuilder(
            applicationContext,
            WallpaperDataBase::class.java,
            WallpaperDataBase.dbName
        ).build()

        showUi()
    }

    private fun showUi(){
        val it : String = intent.getStringExtra(ExtraKey.GET_IMAGE_EXTRA.key)!!
        val uri = Uri.parse(it)

        val inputStream: InputStream = contentResolver.openInputStream(uri)!!
        selectWallpaper = BitmapFactory.decodeStream(inputStream)
        binding.imgAddWallpaperPreview.setImageBitmap(selectWallpaper)

        inputStream.close()
    }

    private val clickSaveWallpaper: (View) -> Unit = { v ->
        val name = binding.edtAddWallpaperWallpaperName.text.toString()
        val wallpaper = Wallpaper(wallpaperName = name, wallpaper = selectWallpaper)

        CoroutineScope(Dispatchers.IO).launch {
            db.wallpaperDao().insert(wallpaper)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@AddWallpaperActivity, "저장 완료", Toast.LENGTH_SHORT).show()

                finish()
            }
        }
    }
}