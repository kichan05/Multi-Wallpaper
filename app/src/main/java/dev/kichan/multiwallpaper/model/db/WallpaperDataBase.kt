package dev.kichan.multiwallpaper.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.kichan.multiwallpaper.model.data.Wallpaper

@Database(
    version = 4,
    entities = [Wallpaper::class]
)
@TypeConverters(WallpaperDBTypeConverter::class)
abstract class WallpaperDataBase : RoomDatabase() {
    abstract fun wallpaperDao() : WallpaperDBDao

    companion object {
        const val dbName = "wallpaper-database"
        private var instance : WallpaperDataBase? = null

        @Synchronized
        fun getInstance(context : Context) : WallpaperDataBase? {
            if (instance == null) {
                synchronized(WallpaperDataBase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WallpaperDataBase::class.java,
                        dbName
                    ).build()
                }
            }
            return instance
        }
    }
}