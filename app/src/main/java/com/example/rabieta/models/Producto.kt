package com.example.rabieta.models

import android.os.Parcelable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
class Producto(
    val Id: Int,
    val ImgResource: String,
    val Titulo: String,
    val DescCorta: String,
    val DescLarga: String,
    val Precio: String,
    val PrecioPromo: String,
    val porcentDesc: String,
    val Tipo: String
) : Parcelable {

    constructor() : this(0, "", "", "", "", "", "", "","")
}

@JsonClass(generateAdapter = true)
class productosResponse(
    @Json(name = "productos")
    val productos: List<Producto>
)