package com.example.rabieta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.rabieta.db.OrdenRepository
import com.example.rabieta.models.Orden
import com.example.rabieta.models.Producto
import com.squareup.picasso.Picasso

class ProductoDetalleBebidaActivity : AppCompatActivity() {

    private lateinit var imgBebida: ImageView
    private lateinit var txtDescripcionBebida: TextView
    private lateinit var toolbarBebida: Toolbar
    private lateinit var txtPrecioRealBebida: TextView
    private lateinit var txtPrecioFinalBebida: TextView
    private lateinit var txtDescuentoBebida: TextView
    private lateinit var txtCantOrdenBebida: TextView
    private lateinit var btnAgregarBebida: Button
    private lateinit var rgTamaño: RadioGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_detalle_bebida)
        val producto = intent.extras?.getParcelable<Producto>(PRODUCTO_DETALLE)
        setupUI(producto)
    }

    private fun setupUI(producto: Producto?) {
        setupToolbar(producto?.Titulo) ?: ""
        txtDescripcionBebida = findViewById(R.id.txtDescProductoBebida)
        imgBebida = findViewById(R.id.imgProductoDetalleBebida)
        txtPrecioFinalBebida = findViewById(R.id.txtPrecioFinalBebida)
        txtPrecioRealBebida = findViewById(R.id.txtPrecioRealBebida)
        txtDescuentoBebida = findViewById(R.id.txtDescBebida)
        txtCantOrdenBebida = findViewById(R.id.txtCantOrdenBebida)
        btnAgregarBebida = findViewById(R.id.btnAgregarBebida)
        rgTamaño = findViewById(R.id.rgTamañoBebida)


        Picasso.get().load(producto?.ImgResource.toString()).into(imgBebida)
        txtDescripcionBebida.text = producto?.DescLarga
        txtPrecioRealBebida.text = "$${producto?.Precio}"
        txtPrecioFinalBebida.text = "$${producto?.PrecioPromo}"
        txtDescuentoBebida.text = producto?.porcentDesc

        btnAgregarBebida.setOnClickListener {
            if (VerificaCantidad()) {
                AgregarBebidaAlCarro(producto)
                finish()
            }
        }
    }

    private fun getRadioButon(checkedID: Int): String {

        var nota = ""
        if (checkedID == R.id.rbLata) {
            nota = "LATA"
        }
        if (checkedID == R.id.rbMediaPinta) {
            nota = "MEDIA PINTA"
        }
        if (checkedID == R.id.rbPinta) {
            nota = "PINTA"
        }
        return nota
    }


    private fun AgregarBebidaAlCarro(producto: Producto?) {
        var orden = Orden(producto)
        orden.Cantidad = txtCantOrdenBebida.text.toString()
        orden.NotaAdicionales = getRadioButon(rgTamaño.checkedRadioButtonId)
        OrdenRepository(this@ProductoDetalleBebidaActivity.applicationContext).addOrden(orden).subscribe()
        finish()
    }

    private fun VerificaCantidad(): Boolean {
        return (txtCantOrdenBebida.text.toString().toInt() >= 1)
    }

    private fun setupToolbar(titulo: String?) {
        toolbarBebida = findViewById(R.id.toolbarDetalleBebida)
        setSupportActionBar(toolbarBebida)
        supportActionBar?.title = titulo
    }
}