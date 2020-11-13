package com.example.rabieta.network

import com.example.rabieta.models.Orden
import com.example.rabieta.models.Producto
import com.squareup.moshi.Json
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface OrdenesApi {


    @POST("/ordenes")
    fun EnviarOrden(
         @Body orden: String
    ):Call<List<Orden>>
}