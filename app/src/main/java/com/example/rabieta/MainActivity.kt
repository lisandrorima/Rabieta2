package com.example.rabieta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rabieta.db.ProductosRepository
import com.example.rabieta.models.Producto
import com.example.rabieta.models.productosResponse
import com.example.steam.network.GamesNetworkClient
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {



    private val compositeDisposable = CompositeDisposable()

    private lateinit var text : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()

    }

    private fun setupUI() {
        text = findViewById(R.id.textjso)
        retrieveProdApi()
    }

    //Esto saca los productos desde la BBDD
    private fun retrieveProductos() {
        ProductosRepository(this@MainActivity.applicationContext)
            .getProducto()
            .subscribe(object : SingleObserver<List<Producto>> {

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(productos: List<Producto>) {
                    //adapter.updateGames(games)
                    var content =""
                    for (producto in productos){
                        content+= producto.Id.toString() + producto.Titulo
                        text.append(content)

                    }
                }

                override fun onError(e: Throwable) {
                    Log.i("MainActivity", "Error al obtener los juegos", e)
                }
            })
    }

    private fun retrieveProdApi() {
        GamesNetworkClient.productosApi.GetProductos().enqueue(object : Callback<List<Producto>> {

            override fun onResponse(
                call: Call<List<Producto>>,
                response: Response<List<Producto>>
            ) {
                response.body()
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.e("MainActivity", "Error al obtener los juegos nuevos", t)
            }
        })
    }
}