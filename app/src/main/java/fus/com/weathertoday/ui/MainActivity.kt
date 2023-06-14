package fus.com.weathertoday.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import fus.com.weathertoday.R
import fus.com.weathertoday.databinding.ActivityMainBinding
import fus.com.weathertoday.ui.chargecoin.ChargeCoinActivity
import fus.com.weathertoday.ui.webviewnews.WebViewNewsActivity
import fus.com.weathertoday.utils.Preference


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private var preference : Preference ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupNavigation()
        setupDrawerLayout()
        setFistInstallApp()
    }

    private fun setupDrawerLayout() {
        binding.mainNavView.bringToFront()
        val toggle = ActionBarDrawerToggle(
            this,
            binding.mainDrawerLayout,
            null,
            R.string.text_drawer_open,
            R.string.text_drawer_close
        )
        binding.mainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.mainNavView.setNavigationItemSelectedListener(this)
        binding.mainNavView.setCheckedItem(R.id.buy_coin)
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.mainNavFragment)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.mainNavFragment).navigateUp()
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.buy_coin -> {
                val intent = Intent(this@MainActivity, ChargeCoinActivity::class.java)
                startActivity(intent)
                Snackbar.make(binding.root, "buy_coin", Snackbar.LENGTH_SHORT).show()
            }

            R.id.view_news -> {
                val intent = Intent(this@MainActivity, WebViewNewsActivity::class.java)
                startActivity(intent)
                Snackbar.make(binding.root, "view_news", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun openCloseNavigationDrawer(view: View) {
        if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.mainDrawerLayout.openDrawer(GravityCompat.START)
        }
    }
    fun setFistInstallApp(){
        preference = Preference.buildInstance(this)
        if (preference?.firstInstall == false) {
            preference?.firstInstall = true
            preference?.setValueCoin(10)
        }
    }
}