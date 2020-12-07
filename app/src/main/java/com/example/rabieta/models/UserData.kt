package com.example.rabieta.models

import android.os.Parcelable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import kotlinx.android.parcel.Parcelize

@Parcelize
@DatabaseTable(tableName = "UsersData")
class UserData(
    @DatabaseField(id = true)
    val email: String,
    @DatabaseField
    val password: String
) : Parcelable {
    constructor() :this("","")
}
