package com.example.rabieta.db

import android.content.Context
import com.example.rabieta.models.Orden
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.DELETE

class OrdenRepository(context: Context) {

    private var dao: Dao<Orden, Int>

    init {
        val helper = OpenHelperManager.getHelper(context, DBHelper::class.java)
        dao = helper.getDao(Orden::class.java)
    }


    fun addOrden(orden: Orden): Completable {
        return Completable
            .fromCallable { dao.create(orden) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun deleteOrden(orden: Orden): Completable {
        return Completable
            .fromCallable { dao.delete(orden) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun updateOrden(orden: Orden): Completable {
        return Completable
            .fromCallable { dao.update(orden) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun getOrdenes(): Single<List<Orden>> {
        return Single
            .fromCallable { dao.queryForAll() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }


}