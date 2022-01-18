package com.bilgehankalay.izmirapi.Interfaces

import com.bilgehankalay.izmirapi.Response.ExchangeResponse
import retrofit2.Call
import retrofit2.http.GET

interface ToplanmaYeriDAOInterface {

    @GET("ibb/cbs/afetaciltoplanmaalani")
    fun toplanma_yerleri_al() : Call<ExchangeResponse>

    @GET("ibb/cbs/muhtarliklar")
    fun muhtarliklar_al() : Call<ExchangeResponse>



}