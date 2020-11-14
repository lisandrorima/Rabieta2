package com.example.rabieta.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object OrdenesNetworkClient {

    private const val BASE_URL = "https://morning-rain-8266.getsandbox.com"


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val ordenesApi = retrofit.create(OrdenesApi::class.java)

}