package com.example.rabieta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rabieta.db.ProductosRepository
import com.example.rabieta.models.Producto
//import com.example.rabieta.network.ProductosNetworkClient
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.rabieta.adapter.ProductosAdapter
import com.example.rabieta.adapter.ProductosListener

class MainActivity : AppCompatActivity(), ProductosListener {

    private lateinit var rvProductos : RecyclerView
    private val adapter  : ProductosAdapter by lazy { ProductosAdapter(this) }
    private val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()

    }

    private fun setupUI() {
        rvProductos = findViewById(R.id.rvProductos)
        rvProductos.adapter = adapter
        retrieveProdApi()
    }

    override fun onResume() {
        super.onResume()
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
                        //text.append(content)

                    }
                }

                override fun onError(e: Throwable) {
                    Log.i("MainActivity", "Error al obtener los juegos", e)
                }
            })
    }

    private fun retrieveProdApi() {
        ProductosNetworkClient.productosApi.GetProductos().enqueue(object : Callback<List<Producto>> {

            override fun onResponse(
                call: Call<List<Producto>>,
                response: Response<List<Producto>>
            ) {
                response.body()?.let { adapter.updateGames(it) }

            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.e("MainActivity", "Error al obtener los juegos nuevos", t)
            }
        })
    }

    override fun onProductoClicked(producto: Producto) {
        TODO("Not yet implemented")
    }
}