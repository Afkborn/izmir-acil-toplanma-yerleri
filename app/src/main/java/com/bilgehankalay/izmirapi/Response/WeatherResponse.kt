package com.bilgehankalay.izmirapi.Response

import com.bilgehankalay.izmirapi.Model.HavaDurumu
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("success") @Expose var success : Boolean,
    @SerializedName("city") @Expose var city : String,
    @SerializedName("result") @Expose var result : List<HavaDurumu>
)
