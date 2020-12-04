package com.example.rabieta.preferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rabieta.R

class PreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferece)
        showPreferences()

    }

    private fun showPreferences() {
        val transaction =
        supportFragmentManager
            .beginTransaction().apply {  replace(R.id.container, PreferenceFragment())}
        transaction.commitNowAllowingStateLoss()
    }



    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}


