package com.example.rabieta.db

import android.content.Context
import com.example.rabieta.models.Orden
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OrdenRepository (context: Context){

    private var dao : Dao<Orden,Int>

    init{
        val helper = OpenHelperManager.getHelper(context, DBHelper::class.java)
        dao = helper.getDao(Orden::class.java)
    }


    fun addOrden(orden: Orden) = dao.create(orden)

    fun deleteOrden(orden: Orden) = dao.delete(orden)

    fun updateOrden(orden: Orden) = dao.update(orden)

    fun getOrden(ordenId: Int) = dao.queryForId(ordenId)

    fun getOrdenes(): Single<List<Orden>> {
        return Single
            .fromCallable { dao.queryForAll() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }



}