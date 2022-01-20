package com.bilgehankalay.izmirapi.Interfaces

import com.bilgehankalay.izmirapi.Response.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CollectDaoInterface {

    @GET("weather/getWeather")
    @Headers("authorization: apikey 2ChxxLdG0t1sE8wQVp91sU:5ztro3zzWtESkOoXAZuCR3","content-type: application/json")
    fun hava_durumu_al(
        @Query("data.lang") data_lang : String,
        @Query("data.city") data_city : String
    ) : Call<WeatherResponse>
}