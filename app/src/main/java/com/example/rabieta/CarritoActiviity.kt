package com.example.rabieta

import android.R.attr.data
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.rabieta.adapter.OrdenesAdapter
import com.example.rabieta.db.OrdenRepository
import com.example.rabieta.models.Orden
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CarritoActiviity : AppCompatActivity(), OrdenesAdapter.OrdenesListener {

    private lateinit var rvOrdenes: RecyclerView
    private lateinit var toolbar: Toolbar
    private val adapter: OrdenesAdapter by lazy { OrdenesAdapter(this) }
    private val compositeDisposable = CompositeDisposable()
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var ordenessend: List<Orden>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito_activiity)
        setupUI()
    }

    private fun setupUI() {
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        fabAdd = findViewById(R.id.floatingActionButton)
       // ordenessend = retrieveOrdenes()

        GlobalScope.launch(Dispatchers.Main) {
            ordenessend = withContext(Dispatchers.Default) {
                retrieveOrdenes()
            }
        }
        fabAdd.setOnClickListener { Ordenar(ordenessend) }
        setupToolbar()
        rvOrdenes = findViewById(R.id.rvOrdenes)
        rvOrdenes.adapter = adapter

    }

    private fun Ordenar(ordenes: List<Orden>) {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<List<Orden>>(
            MutableList::class.java
        )
        val jsonStringFromObject =
            jsonAdapter.toJson(List(ordenes.size) { ordenes.first() })

        OrdenesNetworkClient.ordenesApi.EnviarOrden(jsonStringFromObject)

    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.ToolbarCarrito)

    }

    private fun retrieveOrdenes(): List<Orden> {
        var list = listOf<Orden>()
        OrdenRepository(this)
            .getOrdenes()
            .subscribe(object : SingleObserver<List<Orden>> {


                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }


                override fun onError(e: Throwable) {
                    Log.i("MainActivity", "Error al obtener los juegos", e)
                }

                override fun onSuccess(ordenes: List<Orden>) {
                    list = ordenes
                    adapter.updateOrdenes(ordenes)

                }

            })
        return list
    }


    override fun onOrdenesClicked(orden: Orden) {
        TODO("Not yet implemented")
    }
}

