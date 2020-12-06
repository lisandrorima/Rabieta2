package com.example.rabieta.presenters

import com.example.rabieta.models.UserData


interface IRegisterPresenter {
    fun doAddUser(user: String , pass1 : String , pass2 : String)
}