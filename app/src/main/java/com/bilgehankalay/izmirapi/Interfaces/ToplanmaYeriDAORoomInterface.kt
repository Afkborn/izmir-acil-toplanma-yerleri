package com.bilgehankalay.izmirapi.Interfaces

import androidx.room.*
import com.bilgehankalay.izmirapi.Model.ToplanmaYeri

@Dao
interface ToplanmaYeriDAORoomInterface {
    @Insert
    fun onemliYerEkle(toplanmaYeri: ToplanmaYeri)

    @Update
    fun onemliYerGuncelle(toplanmaYeri: ToplanmaYeri)

    @Delete
    fun onemliYerSil(toplanmaYeri: ToplanmaYeri)

    @Query("SELECT * from toplanmayerleri WHERE type = 0")
    fun tumAcilToplanmaYerleriGetir() : List<ToplanmaYeri?>


    @Query("SELECT * from toplanmayerleri WHERE type = 1")
    fun tumMuhtarlikYerleriGetir() : List<ToplanmaYeri?>



    @Query("SELECT * from toplanmayerleri WHERE adi LIKE :aranacakAd")
    fun getOnemliYerWithName(aranacakAd: String) : List<ToplanmaYeri?>

    @Query("SELECT * from toplanmayerleri WHERE enlem = :enlem AND boylam = :boylam")
    fun getOnemliYerWithLL(enlem:Double, boylam:Double) : ToplanmaYeri?
}