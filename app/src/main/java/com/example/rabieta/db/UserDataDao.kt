package com.example.rabieta.db

import android.content.Context
import com.example.rabieta.models.UserData
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserDataDao(context: Context) {
    private var dao: Dao<UserData, String>

    init {
        val helper = OpenHelperManager.getHelper(context, DBHelper::class.java)
        dao = helper.getDao(UserData::class.java)
    }

    fun addUser(userData: UserData): Completable {
        return Completable
            .fromCallable { dao.create(userData) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getUser(mail: String) : Single<UserData>{
        return Single
            .fromCallable{dao.queryForId(mail)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}