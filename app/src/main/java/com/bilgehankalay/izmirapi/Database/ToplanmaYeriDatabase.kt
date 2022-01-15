package com.bilgehankalay.izmirapi.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bilgehankalay.izmirapi.Interfaces.ToplanmaYeriDAORoomInterface
import com.bilgehankalay.izmirapi.Model.ToplanmaYeri


@Database(entities = [ToplanmaYeri::class], version = 1)
abstract class ToplanmaYeriDatabase : RoomDatabase(){
    abstract fun toplanmaYeriDAO() : ToplanmaYeriDAORoomInterface

    companion object{
        private var INSTANCE : ToplanmaYeriDatabase? = null

        fun getirToplanmaYeriDatabase(context : Context) : ToplanmaYeriDatabase? {
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    ToplanmaYeriDatabase::class.java,
                    "toplanmayeri.db"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE

        }
    }

}