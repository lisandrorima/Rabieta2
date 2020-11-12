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
    private lateinit var txtDescBebida : TextView
    private lateinit var toolbarBebida : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_detalle_bebida)
        val producto = intent.extras?.getParcelable<Producto>(PRODUCTO_DETALLE)
        setupUI(producto)
    }

    private fun setupUI(producto: Producto?) {
        setupToolbar(producto?.Titulo)?:""
        txtDescBebida = findViewById(R.id.txtDescProductoBebida)
        imgBebida = findViewById(R.id.imgProductoDetalleBebida)

        Picasso.get().load(producto?.ImgResource.toString()).into(imgBebida)
        txtDescBebida.text= producto?.DescLarga
    }

    private fun setupToolbar(titulo: String?) {
        toolbarBebida = findViewById(R.id.toolbarDetalleBebida)
        setSupportActionBar(toolbarBebida)
        supportActionBar?.title = titulo
    }
}