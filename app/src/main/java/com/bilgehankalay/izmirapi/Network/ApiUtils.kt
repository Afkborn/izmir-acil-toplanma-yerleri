package com.bilgehankalay.izmirapi.Network

import com.bilgehankalay.izmirapi.Interfaces.ToplanmaYeriDAOInterface

class ApiUtils {

    companion object{
        private const val BASE_URL = "https://openapi.izmir.bel.tr/api/"

        fun ToplanmaYeriDAOInterfaceGetir() : ToplanmaYeriDAOInterface {
            return RetrofitClient.getClient(BASE_URL).create(ToplanmaYeriDAOInterface::class.java)
        }

    }
}