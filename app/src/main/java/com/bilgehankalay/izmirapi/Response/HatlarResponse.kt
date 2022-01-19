package com.bilgehankalay.izmirapi.Response

import com.bilgehankalay.izmirapi.Model.Hat
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HatlarResponse(
    @SerializedName("HataMesaj") @Expose var hataMesaj : String,
    @SerializedName("HataVarMi") @Expose var hataVarMi : Boolean,
    @SerializedName("Hatlar") @Expose var hatlar : List<Hat>
)
