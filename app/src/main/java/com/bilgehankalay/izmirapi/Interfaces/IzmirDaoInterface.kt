package com.bilgehankalay.izmirapi.Interfaces

import com.bilgehankalay.izmirapi.Response.HareketSaatleriResponse
import com.bilgehankalay.izmirapi.Response.HatlarResponse
import com.bilgehankalay.izmirapi.Response.OnemliYerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IzmirDaoInterface {

    @GET("ibb/cbs/afetaciltoplanmaalani")
    fun toplanma_yerleri_al() : Call<OnemliYerResponse>

    @GET("ibb/cbs/muhtarliklar")
    fun muhtarliklar_al() : Call<OnemliYerResponse>

    @GET("eshot/hatlar")
    fun hatlari_al() : Call<HatlarResponse>

    @GET("eshot/hareketsaatleri/{hatnumarasi}")
    fun hareket_saatleri_al(
        @Path("hatnumarasi") hatnumarasi : Int
    ) : Call<HareketSaatleriResponse>


}