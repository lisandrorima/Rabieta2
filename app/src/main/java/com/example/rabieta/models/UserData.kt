package com.example.rabieta.models

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "UsersData")
class UserData(
    @DatabaseField(id = true)
    val email: String,
    @DatabaseField
    val password: String
) {}