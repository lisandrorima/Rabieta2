package com.example.rabieta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.rabieta.models.Producto
import com.squareup.picasso.Picasso

class ProductoDetalleBebidaActivity : AppCompatActivity() {

    private lateinit var imgBebida : ImageView
    private lateinit var txtDescripcionBebida : TextView
    private lateinit var toolbarBebida : Toolbar
    private lateinit var txtPrecioRealBebida : TextView
    private lateinit var txtPrecioFinalBebida : TextView
    private lateinit var txtDescuentoBebida : TextView
    private lateinit var txtCantOrdenBebida : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_detalle_bebida)
        val producto = intent.extras?.getParcelable<Producto>(PRODUCTO_DETALLE)
        setupUI(producto)
    }

    private fun setupUI(producto: Producto?) {
        setupToolbar(producto?.Titulo)?:""
        txtDescripcionBebida = findViewById(R.id.txtDescProductoBebida)
        imgBebida = findViewById(R.id.imgProductoDetalleBebida)
        txtPrecioFinalBebida = findViewById(R.id.txtPrecioFinalBebida)
        txtPrecioRealBebida = findViewById(R.id.txtPrecioRealBebida)
        txtDescuentoBebida = findViewById(R.id.txtDescBebida)
        txtCantOrdenBebida = findViewById(R.id.txtCantOrdenBebida)


        Picasso.get().load(producto?.ImgResource.toString()).into(imgBebida)
        txtDescripcionBebida.text= producto?.DescLarga
        txtPrecioRealBebida.text = "$${producto?.Precio}"
        txtPrecioFinalBebida.text = "$${producto?.PrecioPromo}"
        txtDescuentoBebida.text = producto?.porcentDesc
    }

    private fun setupToolbar(titulo: String?) {
        toolbarBebida = findViewById(R.id.toolbarDetalleBebida)
        setSupportActionBar(toolbarBebida)
        supportActionBar?.title = titulo
    }
}