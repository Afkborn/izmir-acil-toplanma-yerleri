package com.bilgehankalay.izmirapi.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
/*
GidisSaat str
BisikletliMiGidis bool
CiftEngelliMiDonus bool
EngelliMiDonus bool
ElektrikliMiDonus bool
BisikletliMiDonus bool
TarifeId int
DonusSaat str
ElektrikliMiGidis bool
EngelliMiGidis bool
CiftEngelliMiGidis bool
Sira int
*/
data class HareketSaati(
    @SerializedName("GidisSaat") @Expose var GidisSaat : String,
    @SerializedName("BisikletliMiGidis") @Expose var BisikletliMiGidis : Boolean,
    @SerializedName("CiftEngelliMiDonus") @Expose var CiftEngelliMiDonus : Boolean,
    @SerializedName("EngelliMiDonus") @Expose var EngelliMiDonus : Boolean,
    @SerializedName("ElektrikliMiDonus") @Expose var ElektrikliMiDonus : Boolean,
    @SerializedName("BisikletliMiDonus") @Expose var BisikletliMiDonus : Boolean,
    @SerializedName("TarifeId") @Expose var TarifeId : Int,
    @SerializedName("DonusSaat") @Expose var DonusSaat : String,
    @SerializedName("ElektrikliMiGidis") @Expose var ElektrikliMiGidis : Boolean,
    @SerializedName("EngelliMiGidis") @Expose var EngelliMiGidis : Boolean,
    @SerializedName("CiftEngelliMiGidis") @Expose var CiftEngelliMiGidis : Boolean,
    @SerializedName("Sira") @Expose var Sira : Int,
) : Serializable
