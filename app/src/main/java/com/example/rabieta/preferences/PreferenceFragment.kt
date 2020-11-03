package com.example.rabieta.preferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.rabieta.R

class PreferenceFragment :PreferenceFragmentCompat(){
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

}