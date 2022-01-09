package com.bilgehankalay.izmirapi.Response

import com.bilgehankalay.izmirapi.Model.ToplanmaYeri
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ExchangeResponse(
    @SerializedName("sayfadaki_kayitsayisi") @Expose var sayfadaki_kayitsayisi : Int,
    @SerializedName("kayit_sayisi") @Expose var kayit_sayisi : Int,
    @SerializedName("sayfa_numarasi") @Expose var sayfa_numarasi : Int,
    @SerializedName("onemliyer") @Expose var onemliyerler: List<ToplanmaYeri>,
    @SerializedName("toplam_sayfa_sayisi") @Expose var toplam_sayfa_sayisi : Int,
)
