package fus.com.weathertoday.ui.webviewnews

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import fus.com.weathertoday.R
import fus.com.weathertoday.databinding.ActivityWebViewNewsBinding
import fus.com.weathertoday.ui.chargecoin.ChargeCoinActivity
import fus.com.weathertoday.utils.Constants
import fus.com.weathertoday.utils.Preference
import fus.com.weathertoday.utils.SharedPreferenceHelper
import javax.inject.Inject


@AndroidEntryPoint
class WebViewNewsActivity : AppCompatActivity() {

    private var preference : Preference? = null

    private lateinit var binding: ActivityWebViewNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view_news)
        preference = Preference.buildInstance(this)
        binding.imvBack.setOnClickListener { finish() }
        setTextHeader()
        val dialogRemove: AlertDialog = AlertDialog.Builder(this).apply {
            setTitle("Mở ")
            setMessage("Bạn dùng 2 vàng để mở chứ ? ")
            setPositiveButton("Đồng ý") { dialog, id ->
                preference?.apply {
                    if (getValueCoin() > 2) {
                        setValueCoin(getValueCoin() - 2)
                        binding.webViewNews.loadUrl("https://nchmf.gov.vn/Kttvsite/vi-VN/1/thoi-tiet-dat-lien-24h-12h2-15.html")
                        Toast.makeText(
                            this@WebViewNewsActivity,
                            "Đã thêm  thành công và trù 2 vàng",
                            Toast.LENGTH_SHORT

                        ).show()
                        setTextHeader()
                    } else startActivity(
                        Intent(
                           this@WebViewNewsActivity,
                            ChargeCoinActivity::class.java
                        )
                    )
                }

            }
            setNegativeButton("Đóng") { dialog, id ->
                dialog.dismiss()
                finish()
            }
        }.create()
        dialogRemove.show()

    }

    private fun setTextHeader() {
        val currentCoin = preference?.getValueCoin()
        binding.chargeTvCoin.text = currentCoin.toString()
    }
}