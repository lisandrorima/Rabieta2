package com.example.rabieta.ui

interface IRegisterActivityView {
    fun showSaving()
    fun hideSaving()
    fun goBack()
    fun showErrorMsg()
    fun invalidUser()
    fun invalidPass1()
    fun invalidPass2()
}