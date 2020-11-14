package com.example.rabieta.network

import com.example.rabieta.models.Orden
import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST


interface OrdenesApi {


    @Headers("Content-Type: application/json")
    @POST("/orden")
    fun enviarOrden(@Body body: List<Orden>) : Call<List<Orden>>

}