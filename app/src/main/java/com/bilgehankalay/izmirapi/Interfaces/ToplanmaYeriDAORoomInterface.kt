package com.bilgehankalay.izmirapi.Interfaces

import androidx.room.*
import com.bilgehankalay.izmirapi.Model.ToplanmaYeri

@Dao
interface ToplanmaYeriDAORoomInterface {
    @Insert
    fun toplanmaYeriEkle(toplanmaYeri: ToplanmaYeri)

    @Update
    fun toplanmaYeriGuncelle(toplanmaYeri: ToplanmaYeri)

    @Delete
    fun toplanmaYeriSil(toplanmaYeri: ToplanmaYeri)

    @Query("SELECT * from toplanmayerleri")
    fun tumToplanmaYerleriGetir() : List<ToplanmaYeri?>



    @Query("SELECT * from toplanmayerleri WHERE adi LIKE :aranacakAd")
    fun getToplanmaYeriWithName(aranacakAd: String) : List<ToplanmaYeri?>

    @Query("SELECT * from toplanmayerleri WHERE enlem = :enlem AND boylam = :boylam")
    fun getToplanmaYeriWithLL(enlem:Double, boylam:Double) : ToplanmaYeri?
}