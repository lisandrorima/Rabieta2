package com.example.rabieta.models

import android.os.Parcelable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
@DatabaseTable(tableName = "Productos")
class Producto (
    @DatabaseField(id = true)
    val Id : Int,
    @DatabaseField
    val ImgResource : String,
    @DatabaseField
    val Titulo : String,
    @DatabaseField
    val DescCorta: String,
    @DatabaseField
    val Precio : String,
    @DatabaseField
    val PrecioPromo : String,
    @DatabaseField
    val porcentDesc : String
): Parcelable {

    constructor() : this(0, "", "", "", "", "", "")
}

@JsonClass(generateAdapter = true)
class productosResponse(
    @Json(name = "productos")
    val productos: List<Producto>
)