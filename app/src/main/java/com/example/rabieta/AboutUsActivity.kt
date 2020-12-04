package com.example.rabieta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class AboutUsActivity : AppCompatActivity() {

    private lateinit var toolbarAUs : Toolbar
    private lateinit var txtTitulo : TextView
    private lateinit var txtDescripcionAbouUs : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        setupUI()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setupUI() {
        setupToolbar()
        txtTitulo = findViewById(R.id.txtTituloAboutUs)
        txtDescripcionAbouUs = findViewById(R.id.txtDescripcionAboutUs)

        txtDescripcionAbouUs.text = getString(R.string.DescripcionAus)
    }

    private fun setupToolbar() {
        toolbarAUs = findViewById(R.id.toolbarAboutUs)
        setSupportActionBar(toolbarAUs)
        supportActionBar?.title = getString(R.string.ToolbarAboutUs)
    }
}