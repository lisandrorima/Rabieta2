package com.example.rabieta.network

import com.example.rabieta.models.Producto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ProductosApi {

    @GET("/productos2")
    fun GetProductos(): Call<List<Producto>>

   // @GET("/favorite_games")
   // fun getFavoriteGames(): Call<List<GameResponse>>

   // @POST("/new_games")
   // fun addNewGames(
   //     @Body games: List<Game>
   // ): Call<List<GameResponse>>

  // @PATCH("/new_games")
  //  fun updateNewGames(
  //      @Body games: List<Game>
  //  ): Call<List<GameResponse>>

}