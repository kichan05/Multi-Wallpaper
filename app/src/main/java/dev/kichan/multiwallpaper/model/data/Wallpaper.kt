package dev.kichan.multiwallpaper.model.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
data class Wallpaper(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var wallpaper: Bitmap? = null,
    @ColumnInfo("create_time_stamp")
    val createTimeStamp: String = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
    @ColumnInfo("one_shot")
    var oneShot : Int? = null

//    @ColumnInfo("wallpaper_name")
//    val wallpaperName: String,
) : Serializable {
    constructor(@DrawableRes imageDrawable: Int, context: Context) : this(
        wallpaper = BitmapFactory.decodeResource(
            context.resources,
            imageDrawable
        )!!

//        wallpaperName = wallpaperName,
    )

    fun getDateTime(): LocalDateTime = LocalDateTime.parse(createTimeStamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}