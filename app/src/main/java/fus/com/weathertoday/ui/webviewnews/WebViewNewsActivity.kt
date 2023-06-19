package fus.com.weathertoday.ui.webviewnews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import fus.com.weathertoday.R
import fus.com.weathertoday.databinding.ActivityWebViewNewsBinding
import fus.com.weathertoday.ui.webviewnews.list_webview.ListWebViewFragment
import fus.com.weathertoday.utils.Preference


@AndroidEntryPoint
class WebViewNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view_news)

    }
}