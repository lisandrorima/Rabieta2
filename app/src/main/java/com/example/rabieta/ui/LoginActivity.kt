package com.example.rabieta.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.preference.PreferenceManager
import com.example.rabieta.R
import com.example.rabieta.Repositories.LoginRepositoryImpl
import com.example.rabieta.Repositories.SharedPrefImpl
import com.example.rabieta.db.UserDataDao
import com.example.rabieta.presenters.ILoginPresenter
import com.example.rabieta.presenters.LoginPresenterImpl
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity(), ILoginActivityView {

    private lateinit var etUserLog: TextInputEditText
    private lateinit var etPassLog: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private val compositeDisposable = CompositeDisposable()
    private lateinit var containerLog: ConstraintLayout
    private lateinit var logingBackgroun: View
    private lateinit var progressBarLog: ProgressBar
    private lateinit var preferences: SharedPreferences
    private val presenter: ILoginPresenter by lazy {
        LoginPresenterImpl(
            this,
            LoginRepositoryImpl(
                UserDataDao(this@LoginActivity.applicationContext),
                compositeDisposable
            ),
            SharedPrefImpl(preferences, this)
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setuUI()
    }

    private fun setuUI() {
        getSharedPref()
        etUserLog = findViewById(R.id.tvUserLog)
        etPassLog = findViewById(R.id.tvPassLog)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
        containerLog = findViewById(R.id.containerlogin)
        logingBackgroun = findViewById(R.id.loginBackground)
        progressBarLog = findViewById(R.id.progressBarLogin)

        btnLogin.setOnClickListener { loginUser() }
        btnRegister.setOnClickListener { launchRegister() }
    }

    private fun launchRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun loginUser() {
        presenter.doLogin(getTextFrom(etUserLog), getTextFrom(etPassLog))
    }

    override fun showLoading() {
        progressBarLog.visibility = View.VISIBLE
        logingBackgroun.visibility = View.VISIBLE
        containerLog.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBarLog.visibility = View.GONE
        logingBackgroun.visibility = View.GONE
        containerLog.visibility = View.VISIBLE
    }

    override fun showError() {
        Toast.makeText(this, getString(R.string.error_login), Toast.LENGTH_LONG).show()
    }

    override fun goToNextScreen() {
        finish()
    }

    override fun invalidUser() {
        etUserLog.error = getString(R.string.error_login_input)
    }

    override fun invalidPass() {
        etPassLog.error = getString(R.string.error_pass_input)
    }

    private fun getTextFrom(editText: EditText) = editText.text.toString()

    private fun getSharedPref() {
        Single.fromCallable {
            PreferenceManager.getDefaultSharedPreferences(this)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<SharedPreferences> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(sharedPref: SharedPreferences) {
                    preferences = sharedPref
                }

                override fun onError(e: Throwable) {
                    Log.i("LoginActivity", "Error al obtener preferencias ", e)
                }
            })
    }

}