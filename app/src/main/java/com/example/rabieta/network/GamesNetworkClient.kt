package com.example.steam.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object GamesNetworkClient {

    private const val BASE_URL = "http://demo6366239.mockable.io/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val productosApi = retrofit.create(ProductosApi::class.java)

}
