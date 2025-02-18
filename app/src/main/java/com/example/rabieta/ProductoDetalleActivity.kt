package com.example.rabieta

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var cbBacon: CheckBox
    private lateinit var cbChedar: CheckBox
    private lateinit var cbSinAderezos: CheckBox

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
        cbBacon = findViewById(R.id.chkBakon)
        cbChedar = findViewById(R.id.chkCheddar)
        cbSinAderezos = findViewById(R.id.chkSinAderezos)

        Picasso.get().load(producto?.ImgResource.toString()).into(imgProducto)
        txtDetalle.text = producto?.DescLarga
        txtPrecioFinalDetCom.text = "$${producto?.PrecioPromo}"
        txtPrecioRealDetCom.text = "$${producto?.Precio}"
        txtDescDetCom.text = producto?.porcentDesc

        btnAddCart.setOnClickListener {
            if (ValidarCantidad()) {
                AgregarComidaAlCarro(producto)
                finish()
            }
        }
    }

    private fun AgregarComidaAlCarro(producto: Producto?) {
        var orden = Orden(producto)
        orden.Cantidad = txtCantOrdenComida.text.toString()
        orden.NotaAdicionales = checkbox_clicked()

        OrdenRepository(this@ProductoDetalleActivity.applicationContext)
            .addOrden(orden)
            .subscribe()
    }

    private fun ValidarCantidad(): Boolean {
        return (txtCantOrdenComida.text.toString().toInt() >= 1)
    }

    private fun setupToolbar(titulo: String?) {
        toolbar = findViewById(R.id.toolbarDetalleComida)
        setSupportActionBar(toolbar)
        supportActionBar?.title = titulo
    }


    fun checkbox_clicked(): String {
        var notas = ""
        if (cbBacon.isChecked()) {
            notas += "Bacon, "
        }
        if (cbChedar.isChecked()) {
            notas += "Cheddar, "
        }
        if (cbSinAderezos.isChecked()) {
            notas += " Sin Aderezos"
        }
        return notas
    }
}