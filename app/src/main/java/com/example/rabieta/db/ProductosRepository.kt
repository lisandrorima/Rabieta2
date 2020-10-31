package com.example.rabieta.db

import android.content.Context
import com.example.rabieta.models.Producto
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductosRepository (context: Context) {

    private var dao: Dao<Producto, Int>

    init {
        val helper = OpenHelperManager.getHelper(context, DBHelper::class.java)
        dao = helper.getDao(Producto::class.java)
    }

    fun addProducto(producto: Producto) = dao.create(producto)

    fun deleteProducto(producto: Producto) = dao.delete(producto)

    fun updateProducto(producto: Producto) = dao.update(producto)



    fun getProducto(productoID: Int) = dao.queryForId(productoID)

    fun getProducto(): Single<List<Producto>> {
        return Single
            .fromCallable { dao.queryForAll() } // crea un observable (Single) a una función que se va a llamar, es decir, la encapsula
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }
}