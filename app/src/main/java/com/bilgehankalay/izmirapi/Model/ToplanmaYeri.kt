package com.bilgehankalay.izmirapi.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
@Entity(tableName = "toplanmayerleri")
data class ToplanmaYeri(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id : Int = 0,


    @ColumnInfo(name = "ilce") @SerializedName("ILCE") @Expose var ilce : String,
    @ColumnInfo(name = "kapino") @SerializedName("KAPINO") @Expose var kapino : String,
    @ColumnInfo(name = "enlem") @SerializedName("ENLEM") @Expose var enlem : Double,
    @ColumnInfo(name = "aciklama") @SerializedName("ACIKLAMA") @Expose var aciklama : String,
    @ColumnInfo(name = "ilceid") @SerializedName("ILCEID") @Expose var ilce_id : Int,
    @ColumnInfo(name = "mahalle") @SerializedName("MAHALLE") @Expose var mahalle : String,
    @ColumnInfo(name = "mahalleid") @SerializedName("MAHALLEID") @Expose var mahalle_id : Int,
    @ColumnInfo(name = "adi") @SerializedName("ADI") @Expose var adi : String,
    @ColumnInfo(name = "yol") @SerializedName("YOL") @Expose var yol : String,
    @ColumnInfo(name = "boylam") @SerializedName("BOYLAM") @Expose var boylam : Double,
) : Serializable
