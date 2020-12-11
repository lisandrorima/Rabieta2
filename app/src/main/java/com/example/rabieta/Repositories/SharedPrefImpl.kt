package com.example.rabieta.Repositories

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.rabieta.LOGED
import com.example.rabieta.MainActivity
import com.example.rabieta.USER_NAME
import com.example.rabieta.db.UserDataDao
import com.example.rabieta.models.UserData

class SharedPrefImpl (
    private var sharedPreferences: SharedPreferences,
    private var context : Context
) :ISharedPreferencesRep {

    override fun GetSP(spName: String, success: () -> Unit, error: () -> Unit) {
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun EditSP(
        spName: String,
        username:String,
        success: (UserData) -> Unit,
        error: () -> Unit
    ) {
        sharedPreferences.edit().apply{
            putBoolean(LOGED,true)
            putString(USER_NAME,username)
            apply()
        }
    }

}