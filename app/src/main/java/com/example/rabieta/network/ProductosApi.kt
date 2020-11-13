package com.example.rabieta.network

import com.example.rabieta.models.Producto
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface ProductosApi {

    @GET("/productos2")
    fun GetProductos(): Call<List<Producto>>

    @GET("/productos/{Id}")
    fun GetProductoByID (@Path("Id") Id : String): Call<Producto>


   // @POST("/new_games")
   // fun addNewGames(
   //     @Body games: List<Game>
   // ): Call<List<GameResponse>>

  // @PATCH("/new_games")
  //  fun updateNewGames(
  //      @Body games: List<Game>
  //  ): Call<List<GameResponse>>

}