package com.example.rabieta

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
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
import com.example.rabieta.adapter.ProductosAdapter
import com.example.rabieta.adapter.ProductosListener
import com.google.zxing.qrcode.encoder.QRCode
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

const val PRODUCTO_DETALLE = "productoDetalle"

class MainActivity : AppCompatActivity(), ProductosListener {
    var i = ""
    private lateinit var rvProductos: RecyclerView
    private val adapter: ProductosAdapter by lazy { ProductosAdapter(this) }
    private val compositeDisposable = CompositeDisposable()
    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()

    }

    private fun setupUI() {
        retrieveProdApi()
        setupToolbar()
        DarkModePref()
        rvProductos = findViewById(R.id.rvProductos)
        rvProductos.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        DarkModePref()
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
                    adapter.updateGames(productos)
                    var content = ""
                    for (producto in productos) {
                        content += producto.Id.toString() + producto.Titulo
                        //text.append(content)

                    }
                }

                override fun onError(e: Throwable) {
                    Log.i("MainActivity", "Error al obtener los juegos", e)
                }
            })
    }

    private fun retrieveProdApi() {
        ProductosNetworkClient.productosApi.GetProductos()
            .enqueue(object : Callback<List<Producto>> {

                override fun onResponse(
                    call: Call<List<Producto>>,
                    response: Response<List<Producto>>
                ) {
                    response.body()?.let {
                        adapter.updateGames(it)
                    }
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
        when (item.itemId) {
            R.id.it_settings -> launchSettings()
            //R.id.it_aboutUs ->
            R.id.it_cam -> launchCamActivity()

        }

        return super.onOptionsItemSelected(item)
    }

    private fun launchSettings() {
        val intent = Intent(this, PreferenceActivity::class.java)
        startActivity(intent)
    }

    override fun onProductoClicked(producto: Producto) {
        if (producto.Tipo == "Bebida") {
            startActivity(
                Intent(this, ProductoDetalleBebidaActivity::class.java)
                    .putExtra(PRODUCTO_DETALLE, producto)
            )
        } else {
            startActivity(
                Intent(this, ProductoDetalleActivity::class.java)
                    .putExtra(PRODUCTO_DETALLE, producto)
            )
        }
    }

    private fun launchCamActivity() {
        val intent = Intent(this, QrActivity::class.java)
        startActivity(intent)
    }

    private fun DarkModePref() {
        Single.fromCallable { preferences.getBoolean("swDarkMode", true) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Boolean> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(swDarkMode: Boolean) {
                    if (swDarkMode) {
                        AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_YES
                        );
                    } else {
                        AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_NO
                        );
                    }
                }

                override fun onError(e: Throwable) {
                    Log.i("MainActivity", "Error al obtener preferencias - shouldShowFabAdd", e)
                }
            })
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {   //CONSULTAR!!!!
        if (!preferences.getBoolean("swHideQr", false)) {
            toolbar.getMenu().findItem(R.id.it_cam).setVisible(true)
        } else {
            toolbar.getMenu().findItem(R.id.it_cam).setVisible(false)
        }
        return super.onPrepareOptionsMenu(menu)
    }

}