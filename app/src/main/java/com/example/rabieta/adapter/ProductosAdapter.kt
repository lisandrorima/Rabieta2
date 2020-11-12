package com.example.rabieta.adapter

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rabieta.R
import com.example.rabieta.models.Producto
import com.squareup.picasso.Picasso


class ProductosAdapter(
    val listener: ProductosListener
) : RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder>() {

    private var producto: List<Producto> = emptyList()

    class ProductosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProducto = itemView.findViewById<ImageView>(R.id.imgProducto)
        val txtTituloProducto: TextView = itemView.findViewById(R.id.txtTituloProducto)
        val txtPrecio: TextView = itemView.findViewById(R.id.txtPrecio)
        val txtPrecioDesc: TextView = itemView.findViewById(R.id.txtPrecioDesc)
        val txtDiscountPercentaje: TextView = itemView.findViewById(R.id.txtDiscountPercentaje)
        val txtDescCortaProd: TextView = itemView.findViewById(R.id.txtDescCortaProd)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductosViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_producto,
                parent,
                false
            )

        return ProductosViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductosViewHolder, position: Int) {
        holder.apply {

            Picasso.get().load(producto[position].ImgResource.toString()).into(imgProducto)
            txtTituloProducto.text = producto[position].Titulo
            txtPrecio.text = "$${producto[position].Precio}"
            txtDescCortaProd.text = producto[position].DescCorta
            txtDiscountPercentaje.text = producto[position].porcentDesc
            txtPrecioDesc.text = "$${producto[position].PrecioPromo}"

            itemView.setOnClickListener {
                listener.onProductoClicked(producto[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return producto.size
    }

    fun updateGames(productos: List<Producto>) {
        this.producto = productos
        notifyDataSetChanged()
    }

}

interface ProductosListener {
    fun onProductoClicked(producto: Producto)
}