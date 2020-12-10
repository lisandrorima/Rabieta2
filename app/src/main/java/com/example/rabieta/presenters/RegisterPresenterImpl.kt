package com.example.rabieta.presenters

import com.example.rabieta.Repositories.ILoginRepository
import com.example.rabieta.models.UserData
import com.example.rabieta.ui.IRegisterActivityView
import com.example.rabieta.ui.RegisterActivity

class RegisterPresenterImpl(
    private val registerView: IRegisterActivityView,
    private val repository: ILoginRepository
) : IRegisterPresenter {
    private var isUserDataValid: Boolean = true
    override fun doAddUser(user: String, pass1: String, pass2: String) {
        registerView.showSaving()
        validateData(user, pass1, pass2)
        if (isUserDataValid) {
            val userData = createUserData(user, pass1)
            repository
                .addUser(userData, {
                    registerView.hideSaving()
                    registerView.goBack()
                }, {
                    registerView.hideSaving()
                    registerView.showErrorMsg()
                })
        }
        else{
            registerView.hideSaving()
        }
    }

    private fun validateData(user: String, pass1: String, pass2: String) {
        isUserDataValid = true
        validateUser(user)
        validatePass1(pass1)
        validatePass2(pass1, pass2)
    }

    private fun validatePass2(pass1: String, pass2: String) {
        if (pass1 != pass2) {
            registerView.invalidPass2()
            isUserDataValid = false
        }
    }

    private fun validatePass1(pass1: String) {
        if (pass1.isEmpty() || pass1.length < 4) {
            registerView.invalidPass1()
            isUserDataValid = false
        }
    }

    private fun validateUser(user: String) {
        if (user.isEmpty()) {
            registerView.invalidUser()
            isUserDataValid = false
        }
    }

    private fun createUserData(user: String, pass1: String): UserData {
        return UserData(user, pass1)
    }

}