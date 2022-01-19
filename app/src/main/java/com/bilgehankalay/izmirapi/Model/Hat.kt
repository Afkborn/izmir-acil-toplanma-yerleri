package com.bilgehankalay.izmirapi.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
HimHatId int
Aciklama str
HatBitis str
HatBaslangic str
Tarife str
Adi str
HatNo int
HatId int
GuzergahAciklama str
CalismaSaatiDonus str
CalismaSaatiGidis str
*/
@Entity(tableName = "hatlar")
data class Hat (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id : Int = 0,
    @ColumnInfo(name = "him_hat_id") @SerializedName("HimHatId") @Expose var HimHatId : Int,
    @ColumnInfo(name = "aciklama") @SerializedName("Aciklama") @Expose var Aciklama : String,
    @ColumnInfo(name = "hat_bitis") @SerializedName("HatBitis") @Expose var HatBitis : String,
    @ColumnInfo(name = "hat_baslangigc") @SerializedName("HatBaslangic") @Expose var HatBaslangic : String,
    @ColumnInfo(name = "tarife") @SerializedName("Tarife") @Expose var Tarife : String,
    @ColumnInfo(name = "adi") @SerializedName("Adi") @Expose var Adi : String,
    @ColumnInfo(name = "hat_no") @SerializedName("HatNo") @Expose var HatNo : Int,
    @ColumnInfo(name = "hat_id") @SerializedName("HatId") @Expose var HatId : Int,
    @ColumnInfo(name = "guzergah_aciklama") @SerializedName("GuzergahAciklama") @Expose var GuzergahAciklama : String,
    @ColumnInfo(name = "calisma_saati_donus") @SerializedName("CalismaSaatiDonus") @Expose var CalismaSaatiDonus : String,
    @ColumnInfo(name = "calisma_saati_gidis") @SerializedName("CalismaSaatiGidis") @Expose var CalismaSaatiGidis : String,
 ) : Serializable