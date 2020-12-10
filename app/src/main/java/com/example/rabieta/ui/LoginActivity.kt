package com.example.rabieta.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import io.reactivex.disposables.CompositeDisposable

class LoginActivity : AppCompatActivity() , ILoginActivityView{

    private lateinit var etUserLog : TextInputEditText
    private lateinit var etPassLog: TextInputEditText
    private lateinit var btnLogin : Button
    private lateinit var btnRegister : Button
    private val compositeDisposable = CompositeDisposable()
    private lateinit var containerLog : ConstraintLayout
    private lateinit var logingBackgroun : View
    private lateinit var progressBarLog : ProgressBar
    private val preferences :SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(this) }
    private val presenter : ILoginPresenter by lazy{
        LoginPresenterImpl(
            this,
            LoginRepositoryImpl(
                UserDataDao(this@LoginActivity.applicationContext),
                compositeDisposable
            ),
            SharedPrefImpl(preferences,this)
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setuUI()
    }

    private fun setuUI() {
        etUserLog = findViewById(R.id.tvUserLog)
        etPassLog = findViewById(R.id.tvPassLog)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
        containerLog = findViewById(R.id.containerlogin)
        logingBackgroun = findViewById(R.id.loginBackground)
        progressBarLog = findViewById(R.id.progressBarLogin)

        btnLogin.setOnClickListener { loginUser() }

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
}