package dev.kichan.multiwallpaper.model.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Entity
data class Wallpaper(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var wallpaper: Bitmap? = null,
    @ColumnInfo("create_time_stamp")
    val createTimeStamp: String = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
//    @ColumnInfo("wallpaper_name")
//    val wallpaperName: String,
) : Serializable {
    constructor(@DrawableRes imageDrawable: Int, context: Context) : this(
//        wallpaperName = wallpaperName,
        wallpaper = BitmapFactory.decodeResource(
            context.resources,
            imageDrawable
        )!!
    )

    fun getDateTime(): LocalDateTime = LocalDateTime.parse(createTimeStamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}