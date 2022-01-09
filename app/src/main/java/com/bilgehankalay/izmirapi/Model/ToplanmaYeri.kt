package com.bilgehankalay.izmirapi.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ToplanmaYeri(
    @SerializedName("ILCE") @Expose var ilce : String,
    @SerializedName("KAPINO") @Expose var kapino : String,
    @SerializedName("ENLEM") @Expose var enlem : Double,
    @SerializedName("ACIKLAMA") @Expose var aciklama : String,
    @SerializedName("ILCEID") @Expose var ilce_id : Int,
    @SerializedName("MAHALLE") @Expose var mahalle : String,
    @SerializedName("MAHALLEID") @Expose var mahalle_id : Int,
    @SerializedName("ADI") @Expose var adi : String,
    @SerializedName("YOL") @Expose var yol : String,
    @SerializedName("BOYLAM") @Expose var boylam : Double,
) : Serializable
