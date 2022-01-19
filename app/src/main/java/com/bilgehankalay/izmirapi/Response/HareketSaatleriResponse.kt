package com.bilgehankalay.izmirapi.Response

import com.bilgehankalay.izmirapi.Model.HareketSaati
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HareketSaatleriResponse(
    @SerializedName("HareketSaatleriHici") @Expose var HareketSaatlerHici : List<HareketSaati>,
    @SerializedName("HareketSaatleriCtesi") @Expose var HareketSaatleriCtesi : List<HareketSaati>,
    @SerializedName("HareketSaatleriPazar") @Expose var HareketSaatleriPazar : List<HareketSaati>,
)
