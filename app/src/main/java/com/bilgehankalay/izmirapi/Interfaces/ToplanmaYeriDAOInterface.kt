package com.bilgehankalay.izmirapi.Interfaces

import com.bilgehankalay.izmirapi.Response.ExchangeResponse
import retrofit2.Call
import retrofit2.http.GET

interface ToplanmaYeriDAOInterface {

    @GET("ibb/cbs/afetaciltoplanmaalani")
    fun onemliYerAl() : Call<ExchangeResponse>
}