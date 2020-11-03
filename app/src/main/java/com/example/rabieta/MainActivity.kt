package com.example.rabieta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.rabieta.db.ProductosRepository
import com.example.rabieta.models.Producto
import com.example.rabieta.preferences.PreferenceActivity
//import com.example.rabieta.network.ProductosNetworkClient
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {



    private val compositeDisposable = CompositeDisposable()

    private lateinit var text : TextView
    private lateinit var toolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()

    }

    private fun setupUI() {

        //text = findViewById(R.id.textjso)
        setupToolbar()
        retrieveProdApi()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.ToolbarTittle)
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
        ProductosNetworkClient.productosApi.GetProductos().enqueue(object : Callback<List<Producto>> {

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.it_settings -> launchSettings()
            //R.id.it_aboutUs ->
            //R.id.it_cam ->

        }


        return super.onOptionsItemSelected(item)
    }

    private fun launchSettings() {
        val intent = Intent(this, PreferenceActivity::class.java)
        startActivity(intent)
    }
}