package com.example.rabieta.Repositories

import com.example.rabieta.models.UserData


interface ILoginRepository {
    fun addUser(userData : UserData, success:()-> Unit, error: ()-> Unit)
}