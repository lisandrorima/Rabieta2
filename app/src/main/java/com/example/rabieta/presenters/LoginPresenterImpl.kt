package com.example.rabieta.presenters

import android.content.SharedPreferences
import com.example.rabieta.PRODUCTO_DETALLE
import com.example.rabieta.Repositories.ILoginRepository
import com.example.rabieta.Repositories.ISharedPreferencesRep
import com.example.rabieta.USER_NAME
import com.example.rabieta.ui.ILoginActivityView

class LoginPresenterImpl(
    private val view: ILoginActivityView,
    private val repository: ILoginRepository,
    private val sharedRepository: ISharedPreferencesRep
) : ILoginPresenter {
    private var isValidData = true
    override fun doLogin(user: String, pass: String) {
        view.showLoading()

        validateData(user, pass)
        if (isValidData){
            repository
                .getUser(user, {
                    if ((it.email == user) && (it.password == pass)) {
                        sharedRepository.GetSP(PRODUCTO_DETALLE,{},{})
                        sharedRepository.EditSP(PRODUCTO_DETALLE, it.email,{},{})
                        view.hideLoading()
                        view.goToNextScreen()
                    }

                }, {
                    view.hideLoading()
                    view.showError()
                })
        }else{
            view.hideLoading()
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