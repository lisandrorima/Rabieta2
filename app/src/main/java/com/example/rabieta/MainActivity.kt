package com.example.rabieta

import ProductosNetworkClient
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rabieta.adapter.ProductosAdapter
import com.example.rabieta.adapter.ProductosListener
import com.example.rabieta.models.Producto
import com.example.rabieta.preferences.PreferenceActivity
import com.example.rabieta.ui.LoginActivity
import com.example.rabieta.ui.RegisterActivity
import com.google.android.material.navigation.NavigationView
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val PRODUCTO_DETALLE = "productoDetalle"
const val LOGED = "loged"
const val USER_NAME = "userName"


class MainActivity : AppCompatActivity(), ProductosListener {

    private var menuMain: Menu? = null
    private lateinit var rvProductos: RecyclerView
    private val adapter: ProductosAdapter by lazy { ProductosAdapter(this) }
    private val compositeDisposable = CompositeDisposable()
    private lateinit var preferences: SharedPreferences
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
    }

    private fun setupUI() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        initPref()
        rvProductos = findViewById(R.id.rvProductos)
        retrieveProdApi()
        setupToolbar()
        rvProductos.adapter = adapter
    }

    override fun onResume() {
        retrieveProdApi()
        CamQRPref()
        DarkModePref()
        setupDrawer()
        super.onResume()
    }


    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.ToolbarTittle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setVisibilityLogued(username: String?) {

        navView.getHeaderView(0).findViewById<TextView>(R.id.nav_header_textView).text = username
        navView.getHeaderView(0).findViewById<TextView>(R.id.nav_header_textView).visibility =
            View.VISIBLE
        navView.menu.findItem(R.id.it_login).setVisible(false)
        navView.menu.findItem(R.id.it_reg).setVisible(false)

    }

    private fun setVisibilityNotLogued() {
        navView.getHeaderView(0).findViewById<TextView>(R.id.nav_header_textView).visibility =
            View.GONE
        navView.menu.findItem(R.id.it_login).isVisible = true
        navView.menu.findItem(R.id.it_reg).isVisible = true
        navView.menu.clear()
        navView.inflateMenu(R.menu.nav_item)
    }

    private fun setupDrawer() {

        navView = findViewById(R.id.navigationView)

        drawerLayout = findViewById(R.id.drawerLayout)
        if (preferences.getBoolean(LOGED, false)) {

            setVisibilityLogued(preferences.getString(USER_NAME, ""))
        } else {
            setVisibilityNotLogued()
        }

        val drawertoggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(drawertoggle)

        drawertoggle.syncState()
        selectNavigation()
    }

    private fun selectNavigation() {
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.it_carrito -> {
                    this.drawerLayout.closeDrawer(GravityCompat.START)
                    lauchCarritoActivity()
                    true
                }
                R.id.it_reg -> {
                    this.drawerLayout.closeDrawer(GravityCompat.START)
                    launchRegister()
                    true
                }
                R.id.it_login -> {
                    this.drawerLayout.closeDrawer(GravityCompat.START)
                    launchLogin()
                    true
                }
                R.id.it_aboutUs -> {
                    this.drawerLayout.closeDrawer(GravityCompat.START)
                    launchAboutUsActivity()
                    true
                }
                R.id.it_deslog -> {
                    this.drawerLayout.closeDrawer(GravityCompat.START)
                    setLogout()
                    setupDrawer()
                    true
                }
                else -> {
                    false
                }
            }
        }

    }

    private fun setLogout() {
        preferences.edit().apply {
            putBoolean(LOGED, false)
            putString(USER_NAME, "")
            apply()
        }
    }

    private fun initPref() {
        if (!(preferences.getBoolean(LOGED, false))) {
            preferences.edit().apply {
                putBoolean(LOGED, false)
                putString(USER_NAME, "")
                apply()
            }
        }

    }

    private fun retrieveProdApi() {
        ProductosNetworkClient.productosApi.GetProductos()
            .enqueue(object : Callback<List<Producto>> {

                override fun onResponse(
                    call: Call<List<Producto>>,
                    response: Response<List<Producto>>
                ) {
                    response.body()?.let {
                        adapter.updateGames(it)

                    }
                }

                override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                    Log.e("MainActivity", "Error al obtener los productos", t)
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menuMain = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.it_settings -> launchSettings()
            R.id.it_aboutUs -> launchAboutUsActivity()
            R.id.it_cam -> launchCamActivity()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onProductoClicked(producto: Producto) {
        if (producto.Tipo == "Bebida") {
            startActivity(
                Intent(this, ProductoDetalleBebidaActivity::class.java)
                    .putExtra(PRODUCTO_DETALLE, producto)
            )
        } else {
            startActivity(
                Intent(this, ProductoDetalleActivity::class.java)
                    .putExtra(PRODUCTO_DETALLE, producto)
            )
        }
    }


    private fun DarkModePref() {
        Single.fromCallable {
            preferences.getBoolean(
                "swDarkMode",
                true
            )
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Boolean> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(swDarkMode: Boolean) {
                    if (swDarkMode) {
                        AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_YES
                        )
                        delegate.applyDayNight()
                    } else {
                        AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_NO
                        )
                        delegate.applyDayNight()

                    }
                }

                override fun onError(e: Throwable) {
                    Log.i("MainActivity", "Error al obtener preferencias ", e)
                }
            })
    }

    private fun CamQRPref() {
        Single.fromCallable { preferences.getBoolean("swHideQr", true) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Boolean> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(swHideQr: Boolean) {
                    if (swHideQr) {
                        menuMain?.findItem(R.id.it_cam)?.isVisible = false

                    } else {
                        menuMain?.findItem(R.id.it_cam)?.isVisible = true
                    }
                }

                override fun onError(e: Throwable) {
                    Log.i("MainActivity", "Error al obtener preferencias - CamQRPref", e)
                }
            })
    }

    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.openDrawer(navView)
        return true
    }


    private fun launchLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun launchCamActivity() {
        startActivity(Intent(this, QrActivity::class.java))
    }

    private fun launchAboutUsActivity() {
        startActivity(Intent(this, AboutUsActivity::class.java))
    }

    private fun launchRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun lauchCarritoActivity() {
        startActivity(Intent(this, CarritoActiviity::class.java))
    }

    private fun launchSettings() {
        startActivity(Intent(this, PreferenceActivity::class.java))
    }
}