package com.example.rabieta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.rabieta.models.Producto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.round

class ProductoDetalleActivity : AppCompatActivity() {

    private lateinit var txtResultado : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_detalle)
        val bundle = intent.extras
        val resultado = bundle?.getString(RESULTADO)?.toInt() ?:0
        setupUI(resultado)
    }

    //con el resultado hacer un query by ID
    private fun setupUI(resultado: Any) {
        txtResultado = findViewById(R.id.txtResultado)
        txtResultado.setText(resultado.toString())
    }

    private fun retrieveProductoByIDApi() {
        ProductosNetworkClient.productosApi.GetProductos().enqueue(object :
            Callback<List<Producto>> {

            override fun onResponse(
                call: Call<List<Producto>>,
                response: Response<List<Producto>>
            ) {
                //response.body()?.let { adapter.updateGames(it) }

            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.e("MainActivity", "Error al obtener los juegos nuevos", t)
            }
        })
    }
}