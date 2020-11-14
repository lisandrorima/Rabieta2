package com.example.rabieta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

    class OrdenesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val imgOrdenOrd = itemView.findViewById<ImageView>(R.id.imgOrden)
        val txtTituloOrden: TextView = itemView.findViewById(R.id.txtTituloOrden)
        val txtPrecio: TextView = itemView.findViewById(R.id.txtPrecioUnit)
        val txtCant: TextView = itemView.findViewById(R.id.txtcantidad)
        val btnEliminarOrden: Button = itemView.findViewById(R.id.btnEliminarOrden)
        val btnadd: Button = itemView.findViewById(R.id.btnAgregarUnidad)
        val btnQuitar: Button = itemView.findViewById(R.id.btnQuitarUnidad)
        val txtNotas: TextView = itemView.findViewById(R.id.txtNotaAdicionalesOrden)

        override fun onClick(v: View?) {
        }
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

            Picasso.get().load(orden[position].ImgOrden).into(imgOrdenOrd)
            txtTituloOrden.text = orden[position].Titulo
            txtCant.text = orden[position].Cantidad
            txtPrecio.text = "$${((orden[position].PrecioPromo?.toInt() ?: 0) * orden[position].Cantidad.toInt()).toString()}"
            txtNotas.text = orden[position].NotaAdicionales
            btnEliminarOrden.setOnClickListener {
                listener.onDeleteClicked(orden[position])
            }
            btnQuitar.setOnClickListener {
                listener.onRemove(orden[position])
            }
            btnadd.setOnClickListener {
                listener.onAdd(orden[position])
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
        fun onDeleteClicked(orden: Orden)
        fun onAdd(orden: Orden)
        fun onRemove(orden: Orden)

    }
}