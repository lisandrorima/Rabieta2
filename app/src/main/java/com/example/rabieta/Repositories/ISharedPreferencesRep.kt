package com.example.rabieta.Repositories

import android.content.SharedPreferences
import com.example.rabieta.models.UserData

interface ISharedPreferencesRep {

    fun GetSP(spName :String, success:()-> Unit, error: ()-> Unit)
    fun EditSP(spName :String, userName :String, success: (UserData) -> Unit, error: () -> Unit)
}