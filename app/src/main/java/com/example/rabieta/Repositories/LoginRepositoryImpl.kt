package com.example.rabieta.Repositories


import com.example.rabieta.db.UserDataDao
import com.example.rabieta.models.UserData
import io.reactivex.disposables.CompositeDisposable

class LoginRepositoryImpl(
    private val dataUserDao: UserDataDao,
    private val compositeDisposable: CompositeDisposable
) : ILoginRepository {
    override fun addUser(userData: UserData, success: () -> Unit, error: () -> Unit) {
        compositeDisposable.add(
            dataUserDao
                .addUser(userData)
                .subscribe({
                    success()
                }, {
                    error()
                })
        )

    }

    override fun getUser(email: String, success: (UserData) -> Unit, error: () -> Unit) {
        compositeDisposable.add(
            dataUserDao
                .getUser(email)
                .subscribe({
                    success(it)
                }, {
                    error()
                })
        )
    }


}