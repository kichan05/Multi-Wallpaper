package dev.kichan.multiwallpaper.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.kichan.multiwallpaper.model.data.Wallpaper

@Dao
interface WallpaperDBDao {
    @Insert
    fun insert(wallpaper: Wallpaper)

    @Query("SELECT * FROM Wallpaper")
    fun getAll() : List<Wallpaper>
}