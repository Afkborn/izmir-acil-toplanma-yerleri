package com.bilgehankalay.izmirapi.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/*
            "date": "20.01.2022",
            "day": "Perşembe",
            "icon": "https://cdnydm.com/media/7FbW8fgzadAEm9nU6aT7Iw.png",
            "description": "parçalı bulutlu",
            "status": "Clouds",
            "degree": "-4.42",
            "min": "-7.85",
            "max": "-2.2",
            "night": "-5.33",
            "humidity": "66"

 */
data class HavaDurumu(
    @SerializedName("date") @Expose var date : String,
    @SerializedName("day") @Expose var day : String,
    @SerializedName("icon") @Expose var icon : String,
    @SerializedName("description") @Expose var description : String,
    @SerializedName("status") @Expose var status : String,
    @SerializedName("degree") @Expose var degree : String,
    @SerializedName("min") @Expose var min : String,
    @SerializedName("max") @Expose var max : String,
    @SerializedName("night") @Expose var night : String,
    @SerializedName("humidity") @Expose var humidity : String,
) : Serializable
