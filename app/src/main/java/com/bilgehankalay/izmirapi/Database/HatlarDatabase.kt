package com.bilgehankalay.izmirapi.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bilgehankalay.izmirapi.Interfaces.HatDAO
import com.bilgehankalay.izmirapi.Model.Hat


@Database(entities = [Hat::class], version = 1)
abstract class HatlarDatabase  : RoomDatabase(){
    abstract  fun hatlarDAO() : HatDAO

    companion object{
        private  var INSTANCE : HatlarDatabase? = null

        fun getirHatlarDatabase(context : Context) : HatlarDatabase? {
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    HatlarDatabase::class.java,
                    "hatlar.db"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }

}