package com.example.rabieta

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.rabieta.models.Producto
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_qrscanner.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class QrActivity : AppCompatActivity() {
    val CAMERA_PERM = 111
    lateinit var codeScanner: CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscanner)
        setupUI()

    }

    private fun setupUI() {
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {

                if (it.toString().toIntOrNull() != null) {



                    Log.i("act", "antes de traer prod")

                    ProductosNetworkClient.productosApi.GetProductoByID(it.toString())
                        .enqueue(object : Callback<Producto> {


                            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                                Log.i("act", "response")
                                response.body()?.let {

                                    launchDetailActivity(it)
                                }
                            }

                            override fun onFailure(call: Call<Producto>, t: Throwable) {
                                Log.e("QR", "Error al obtener los juegos nuevos", t)
                                Log.i("act", "fallo")
                            }



                        })
                } else {
                    Toast.makeText(this, "Scan result: ${it.text} ", Toast.LENGTH_LONG).show()
                }


            }
        }

        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(this, "Camera error: ${it.message}", Toast.LENGTH_LONG).show()
            }
        }

        checkPermission()
    }

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERM
            )
        } else {
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == CAMERA_PERM && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            codeScanner.startPreview()
        } else {
            Toast.makeText(this, "No se puede activar hasta dar permisos", Toast.LENGTH_LONG).show()
        }

    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun launchDetailActivity(producto: Producto) {

        if (producto.Tipo== "Bebida"){
            val intent = Intent(this, ProductoDetalleBebidaActivity::class.java)
            intent.putExtra(PRODUCTO_DETALLE, producto)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, ProductoDetalleActivity::class.java)
            intent.putExtra(PRODUCTO_DETALLE, producto)
            startActivity(intent)
            finish()
        }


    }


}