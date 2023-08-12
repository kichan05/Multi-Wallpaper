package dev.kichan.multiwallpaper.ui.add

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import dev.kichan.multiwallpaper.ui.LoadingDialog
import dev.kichan.multiwallpaper.ExtraKey
import dev.kichan.multiwallpaper.R
import dev.kichan.multiwallpaper.ui.UiUtil
import dev.kichan.multiwallpaper.model.data.Wallpaper
import dev.kichan.multiwallpaper.databinding.ActivityAddWallpaperBinding
import dev.kichan.multiwallpaper.model.db.WallpaperDataBase
import kotlinx.coroutines.*
import java.io.InputStream

class AddWallpaperActivity : AppCompatActivity() {
    private lateinit var db: WallpaperDataBase
    private lateinit var binding: ActivityAddWallpaperBinding
    lateinit var selectWallpaper: Bitmap

    private val dialog = LoadingDialog()

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

    private fun showUi() {
        val it: String = intent.getStringExtra(ExtraKey.GET_IMAGE_EXTRA.key)!!
        val uri = Uri.parse(it)

        val inputStream: InputStream = contentResolver.openInputStream(uri)!!
        selectWallpaper = BitmapFactory.decodeStream(inputStream)

        binding.imgAddWallpaperPreview.run {
            setImageBitmap(selectWallpaper)
            clipToOutline = true

            val previewSize = UiUtil.getPreViewImageSize(this@AddWallpaperActivity, 80)
            layoutParams.width = previewSize.x
            layoutParams.height = previewSize.y
        }

        inputStream.close()
    }

    private val clickSaveWallpaper: (View) -> Unit = { v ->
//        val name = binding.edtAddWallpaperWallpaperName.text.toString()
        val wallpaper = Wallpaper(wallpaper = selectWallpaper)

        dialog.show(supportFragmentManager, LoadingDialog.TAG)

        CoroutineScope(Dispatchers.IO).launch {
            db.wallpaperDao().insert(wallpaper)
            withContext(Dispatchers.Main) {
                delay(500)

                dialog.dismiss()
                Toast.makeText(this@AddWallpaperActivity, "저장 완료", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}