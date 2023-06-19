package fus.com.weathertoday.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import fus.com.weathertoday.R
import fus.com.weathertoday.databinding.ActivityMainBinding
import fus.com.weathertoday.utils.Preference


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var preference: Preference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setFistInstallApp()
        val adapter = ViewPager(supportFragmentManager)
        binding.viewpager.adapter = adapter
        binding.viewpager.offscreenPageLimit = 2
        binding.mainNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.buy_coin -> {
                    binding.viewpager.currentItem = 0
                }

                R.id.view_news -> {
                    binding.viewpager.currentItem = 1
                }
            }
            true
        }
    }


    fun setFistInstallApp() {
        preference = Preference.buildInstance(this)
        if (preference?.firstInstall == false) {
            preference?.firstInstall = true
            preference?.setValueCoin(10)
        }
    }
}