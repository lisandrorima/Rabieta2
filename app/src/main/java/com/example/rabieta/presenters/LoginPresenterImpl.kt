package com.example.rabieta.presenters

import android.content.SharedPreferences
import com.example.rabieta.Repositories.ILoginRepository
import com.example.rabieta.ui.ILoginActivityView

class LoginPresenterImpl(
    private val view: ILoginActivityView,
    private val repository: ILoginRepository
) : ILoginPresenter {
    private var isValidData = true
    override fun doLogin(user: String, pass: String) {
        view.showLoading()
        validateData(user, pass)
        if (isValidData){
            repository
                .getUser(user, {
                    if ((it.email == user) && (it.password == pass)) {
                        // Agregar al SHARED
                    }
                    view.hideLoading()
                    view.goToNextScreen()
                }, {
                    view.hideLoading()
                    view.showError()
                })
        }
    }


    private fun validateData(user: String, pass: String) {
        validateUser(user)
        validatePass(pass)
    }

    private fun validatePass(pass: String) {
        if (pass.isEmpty()) {
            view.invalidPass()
            isValidData = false
        }
    }

    private fun validateUser(user: String) {
        if (user.isEmpty()) {
            view.invalidUser()
            isValidData = false
        }
    }

}