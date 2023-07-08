package dev.kichan.multiwallpaper.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import dev.kichan.multiwallpaper.model.data.Wallpaper

@Dao
interface WallpaperDBDao {
    @Insert
    fun insert(wallpaper: Wallpaper)

    @Query("SELECT * FROM Wallpaper")
    fun getAll() : List<Wallpaper>

    @Query("SELECT * FROM Wallpaper WHERE id = :id")
    fun getWallpaper(id : Int) : Wallpaper?

    @Query("SELECT * FROM Wallpaper WHERE one_shot IS NOT null")
    fun getOneShot() : List<Wallpaper>

    @Update
    fun update(wallpaper: Wallpaper)
}