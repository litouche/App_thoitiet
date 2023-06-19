package fus.com.weathertoday.ui.webviewnews.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import fus.com.weathertoday.R
import fus.com.weathertoday.databinding.ActivityNewBinding
@AndroidEntryPoint
class NewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new)

        initView()
    }

    private fun initView() {
        binding.toolBar.setNavigationOnClickListener {
           finish()
        }
        binding.webview.loadUrl(intent.getStringExtra("url") ?: "")
    }
}