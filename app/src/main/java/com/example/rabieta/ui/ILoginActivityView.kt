package com.example.rabieta.ui

interface ILoginActivityView {
    fun showLoading()
    fun hideLoading()
    fun showError()
    fun goToNextScreen()
    fun invalidUser()
    fun invalidPass()
}