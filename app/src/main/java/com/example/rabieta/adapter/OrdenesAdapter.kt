package com.example.rabieta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rabieta.R
import com.example.rabieta.models.Orden
import com.squareup.picasso.Picasso


class OrdenesAdapter(
    val listener: OrdenesListener
) : RecyclerView.Adapter<OrdenesAdapter.OrdenesViewHolder>() {

    private var orden: List<Orden> = emptyList()

    class OrdenesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgOrden = itemView.findViewById<ImageView>(R.id.imgProducto)
        val txtTituloOrden: TextView = itemView.findViewById(R.id.txtTituloProducto)
        val txtPrecio: TextView = itemView.findViewById(R.id.txtPrecio)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdenesViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_orden,
                parent,
                false
            )

        return OrdenesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrdenesViewHolder, position: Int) {
        holder.apply {

            Picasso.get().load(orden[position].ImgOrden.toString()).into(imgOrden)
            txtTituloOrden.text = orden[position].Titulo
            txtPrecio.text = "$${orden[position].Precio}"
            //txtPrecioDesc.text = "$${orden[position].PrecioPromo}"

            itemView.setOnClickListener {
                listener.onOrdenesClicked(orden[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return orden.size
    }

    fun updateOrdenes(ordenes: List<Orden>) {
        this.orden = ordenes
        notifyDataSetChanged()
    }

    interface OrdenesListener {
        fun onOrdenesClicked(orden: Orden)
    }
}