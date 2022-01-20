package com.bilgehankalay.izmirapi.Interfaces

import androidx.room.*
import com.bilgehankalay.izmirapi.Model.Hat

@Dao
interface HatDaoRoom {
    @Insert
    fun HatEkle(hat : Hat)

    @Update
    fun HatGuncelle(hat : Hat)

    @Delete
    fun HatSil(hat : Hat)

    @Query("SELECT * FROM hatlar")
    fun tum_hatlari_getir() : List<Hat?>

    @Query("SELECT * FROM hatlar WHERE hat_no = :hat_no")
    fun hat_getir_with_hatno(hat_no : Int) : Hat?
}