package com.bilgehankalay.izmirapi.Network

import com.bilgehankalay.izmirapi.Interfaces.CollectDaoInterface
import com.bilgehankalay.izmirapi.Interfaces.IzmirDaoInterface

class ApiUtils {

    companion object{
        private const val BASE_URL_IZMIR_API = "https://openapi.izmir.bel.tr/api/"
        private const val BASE_URL_COLLECT_API = "https://api.collectapi.com/"

        fun izmirDaoInterfaceGetir() : IzmirDaoInterface {
            return RetrofitClient.getClient(BASE_URL_IZMIR_API).create(IzmirDaoInterface::class.java)
        }
        fun collectApiDaoInterfaceGetir() : CollectDaoInterface {
            return RetrofitClient.getClient(BASE_URL_COLLECT_API).create(CollectDaoInterface::class.java)
        }


    }
}