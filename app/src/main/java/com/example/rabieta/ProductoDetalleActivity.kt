package com.example.rabieta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.rabieta.db.OrdenRepository
import com.example.rabieta.models.Orden
import com.example.rabieta.models.Producto
import com.squareup.picasso.Picasso


class ProductoDetalleActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var imgProducto: ImageView
    private lateinit var txtDetalle: TextView
    private lateinit var txtPrecioFinalDetCom: TextView
    private lateinit var txtPrecioRealDetCom: TextView
    private lateinit var txtDescDetCom: TextView
    private lateinit var txtCantOrdenComida: TextView
    private lateinit var btnAddCart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_detalle)
        val producto = intent.extras?.getParcelable<Producto>(PRODUCTO_DETALLE)
        setupUI(producto)
    }


    private fun setupUI(producto: Producto?) {
        setupToolbar(producto?.Titulo) ?: ""
        imgProducto = findViewById(R.id.imgDetalleComida)
        txtDetalle = findViewById(R.id.txtDetalleComida)
        txtPrecioFinalDetCom = findViewById(R.id.txtPrecioFinalComDet)
        txtPrecioRealDetCom = findViewById(R.id.txtPrecioRealComDet)
        txtDescDetCom = findViewById(R.id.txtDesComDet)
        txtCantOrdenComida = findViewById(R.id.txtCantOrdenComida)
        btnAddCart = findViewById(R.id.btnAgregarComida)


        Picasso.get().load(producto?.ImgResource.toString()).into(imgProducto)
        txtDetalle.text = producto?.DescLarga
        txtPrecioFinalDetCom.text = "$${producto?.PrecioPromo}"
        txtPrecioRealDetCom.text = "$${producto?.Precio}"
        txtDescDetCom.text = producto?.porcentDesc

        btnAddCart.setOnClickListener {
            if (ValidarCantidad()) {
                AgregarComidaAlCarro(producto)
                lauchCarritoActivity()
            }
        }
    }

    private fun AgregarComidaAlCarro(producto: Producto?) {
        var orden = Orden(producto)
        orden.Cantidad = txtCantOrdenComida.text.toString()
        orden.NotaAdicionales = ""
        OrdenRepository(this@ProductoDetalleActivity.applicationContext).addOrden(orden)
    }

    private fun ValidarCantidad(): Boolean {
        return (txtCantOrdenComida.text.toString().toInt() >= 1)
    }

    private fun setupToolbar(titulo: String?) {
        toolbar = findViewById(R.id.toolbarDetalleComida)
        setSupportActionBar(toolbar)
        supportActionBar?.title = titulo
    }

    override fun onBackPressed() {
        //startActivity(Intent(this, MainActivity::class.java))
        finish()
        super.onBackPressed()
    }

    private fun lauchCarritoActivity() {
        val intent = Intent(this, CarritoActiviity::class.java)
        startActivity(intent)
    }
}