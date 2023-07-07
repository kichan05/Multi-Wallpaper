package com.heechan.multiwallpaper.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.heechan.multiwallpaper.Wallpaper

@Dao
interface WallpaperDBDao {
    @Insert
    fun insert(wallpaper: Wallpaper)

    @Query("SELECT * FROM Wallpaper")
    fun getAll() : List<Wallpaper>
}