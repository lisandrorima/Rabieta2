package com.example.rabieta.models

import android.os.Parcelable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
@DatabaseTable(tableName = "Ordenes")
class Orden(
    @DatabaseField(id = true)
    val Id: Int?,
    @DatabaseField
    var Cantidad: String,
    @DatabaseField
    var ImgOrden: String?,
    @DatabaseField
    var Precio: String?,
    @DatabaseField
    val Titulo: String?,
    @DatabaseField
    val DescLarga: String?,
    @DatabaseField
    var PrecioPromo: String?,
    @DatabaseField
    val porcentDesc: String?,
    @DatabaseField
    val Tipo: String?,
    @DatabaseField
    var NotaAdicionales: String?,
) : Parcelable {
    constructor() : this(null, "", "", "", "","","","","","")

    constructor(producto: Producto?) : this(
        null, "", producto?.ImgResource, producto?.PrecioPromo, producto?.Titulo
        ,producto?.DescLarga,producto?.PrecioPromo,producto?.porcentDesc,producto?.Tipo,"")



}