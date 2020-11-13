

import com.example.rabieta.network.OrdenesApi
import com.example.rabieta.network.ProductosApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object ProductosNetworkClient {

    private const val BASE_URL = "http://demo6366239.mockable.io"


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val productosApi = retrofit.create(ProductosApi::class.java)

}

object OrdenesNetworkClient {

    private const val BASE_URL = "https://morning-rain-8266.getsandbox.com"


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val ordenesApi = retrofit.create(OrdenesApi::class.java)

}
