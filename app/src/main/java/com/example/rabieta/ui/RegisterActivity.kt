package com.example.rabieta.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.rabieta.R
import com.example.rabieta.Repositories.LoginRepositoryImpl
import com.example.rabieta.db.UserDataDao
import com.example.rabieta.presenters.IRegisterPresenter
import com.example.rabieta.presenters.RegisterPresenterImpl
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), IRegisterActivityView {
    private lateinit var etUser: TextInputLayout
    private lateinit var etPass1: TextInputLayout
    private lateinit var etPass2: TextInputLayout
    private lateinit var btnRegister: Button
    private lateinit var btnCancel: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var container: ConstraintLayout
    private lateinit var savingBackground: View
    private val compositeDisposable = CompositeDisposable()

    private val presenter: IRegisterPresenter by lazy {
        RegisterPresenterImpl(
            this,
            LoginRepositoryImpl(
                UserDataDao(this@RegisterActivity.applicationContext),
                compositeDisposable
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setupUI()
    }

    private fun setupUI() {
        etUser = findViewById(R.id.textInputUserReg)
        etPass1 = findViewById(R.id.textInputPass1Reg)
        etPass2 = findViewById(R.id.textInputPass2Reg)
        btnRegister = findViewById(R.id.btnReg)
        btnCancel = findViewById(R.id.btnCancel)
        container = findViewById(R.id.container)
        progressBar = findViewById(R.id.progressBar)
        savingBackground = findViewById(R.id.savingBackground)
        btnCancel.setOnClickListener { goBack() }
        btnRegister.setOnClickListener { addUser() }
    }

    private fun addUser() {
        presenter.doAddUser(etUser.editText?.text.toString(), etPass1.editText?.text.toString(), etPass2.getEditText()?.text.toString())
    }

    override fun showSaving() {
        progressBar.visibility = View.VISIBLE
        savingBackground.visibility = View.VISIBLE
        container.visibility = View.GONE
    }

    override fun hideSaving() {
        progressBar.visibility = View.GONE
        savingBackground.visibility = View.GONE
        container.visibility = View.VISIBLE
    }

    override fun goBack() {
        finish()
    }

    override fun showErrorMsg() {
        Toast.makeText(this, getString(R.string.error_register), Toast.LENGTH_LONG).show()
    }

    override fun invalidUser() {
        etUser.error = getString(R.string.reg_error_nombre)

    }

    override fun invalidPass1() {
        etPass1.error = getString(R.string.reg_error_pass1)
    }

    override fun invalidPass2() {
        etPass2.error = getString(R.string.reg_error_pass2)
    }
}