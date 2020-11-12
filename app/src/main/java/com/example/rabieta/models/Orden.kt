package com.example.rabieta.models

import android.os.Parcelable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import kotlinx.android.parcel.Parcelize

@Parcelize
@DatabaseTable(tableName = "Ordenes")
class Orden(
    @DatabaseField (id = true)
    val Id : Int,
    @DatabaseField
    val Cantidad : String,
    @DatabaseField
    val ImgOrden : String,
    @DatabaseField
    val Precio : String,
    @DatabaseField
    val Nota : String
): Parcelable{
    constructor(): this(0,"","","","")
}