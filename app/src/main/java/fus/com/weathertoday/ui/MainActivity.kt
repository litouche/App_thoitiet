package fus.com.weathertoday.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
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
    }

    override fun onSupportNavigateUp() = findNavController(R.id.mainNavFragment).navigateUp()

    private fun setFistInstallApp() {
        preference = Preference.buildInstance(this)
        if (preference?.firstInstall == false) {
            preference?.firstInstall = true
            preference?.setValueCoin(10)
        }
    }
}