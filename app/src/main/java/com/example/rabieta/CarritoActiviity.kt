package com.example.rabieta

import android.R.attr.data
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.rabieta.adapter.OrdenesAdapter
import com.example.rabieta.db.OrdenRepository
import com.example.rabieta.models.Orden
import com.example.rabieta.network.OrdenesNetworkClient
import com.example.rabieta.notifications.OrdenNotifChannelManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

const val NOTIF_ORDEN_KEY = "ORDEN"

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
        retrieveOrdenes()
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        fabAdd = findViewById(R.id.floatingActionButton)
      //  setupToolbar()
        rvOrdenes = findViewById(R.id.rvOrdenes)
        rvOrdenes.adapter = adapter
        fabAdd.setOnClickListener { post(ordenessend) }

    }

    private fun post(ordenes: List<Orden>) {
        OrdenesNetworkClient.ordenesApi.enviarOrden(ordenes)
            .enqueue(object : retrofit2.Callback<List<Orden>> {

                override fun onResponse(call: Call<List<Orden>>, response: Response<List<Orden>>) {
                    for (orden in ordenes) {
                        OrdenRepository(this@CarritoActiviity.applicationContext)
                            .deleteOrden(orden)

                    }
                    showNotification()
                    finish()
                }

                override fun onFailure(call: Call<List<Orden>>, t: Throwable) {
                    Log.e("Carrito", "Error al mandar la info", t)
                }

            })
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.ToolbarCarrito)

    }

    override fun onResume() {
        retrieveOrdenes()
        super.onResume()
    }


    private fun retrieveOrdenes() {

        OrdenRepository(this)
            .getOrdenes()
            .subscribe(object : SingleObserver<List<Orden>> {


                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }


                override fun onError(e: Throwable) {
                    Log.i("MainActivity", "Error al obtener los pedidos", e)
                }

                override fun onSuccess(ordenes: List<Orden>) {
                    adapter.updateOrdenes(ordenes)
                    ordenessend = ordenes

                }

            })

    }

    private fun deleteOrden(orden: Orden){
        compositeDisposable.add(
            OrdenRepository(this@CarritoActiviity.applicationContext)
                .deleteOrden(orden)
                .subscribe()
    )}


    override fun onDeleteClicked(orden: Orden) {
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle(orden.Titulo)
            .setMessage("Desea eliminar  ${orden.Titulo} del carrito")
            .setPositiveButton("BORRAR") { _, _ ->

                deleteOrden(orden)
                onResume()
            }
            .setNegativeButton("CANCELAR") { _, _ ->

            }

            .setCancelable(false)
            .show()


    }


    override fun onAdd(orden: Orden) {
        var actualizada: Int
        actualizada = orden.Cantidad.toInt()
        actualizada += 1

        orden.Cantidad = actualizada.toString()
        OrdenRepository(this).updateOrden(orden).subscribe()
        onResume()
    }

    override fun onRemove(orden: Orden) {
        var actualizada: Int
        if (orden.Cantidad.toInt() > 1) {
            actualizada = orden.Cantidad.toInt()
            actualizada -= 1

            orden.Cantidad = actualizada.toString()
            OrdenRepository(this).updateOrden(orden).subscribe()
        } else {
            onDeleteClicked(orden)
        }
        onResume()
    }


    private fun showNotification() {
        OrdenNotifChannelManager.createNotificationChannelForOrden(this)

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            putExtra(NOTIF_ORDEN_KEY, "Notificacion")
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        NotificationCompat.Builder(this, OrdenNotifChannelManager.NEW_ORDEN_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Pedido enviado")
            .setContentText("Estara recibiendo su pedido a la brevedad")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
            .also { notification ->
                NotificationManagerCompat
                    .from(this)
                    .notify(1, notification)
            }
    }
}

