package fus.com.weathertoday.ui.webviewnews.list_webview

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import fus.com.weathertoday.databinding.FragmentListWebViewBinding
import fus.com.weathertoday.ui.BaseFragment
import fus.com.weathertoday.ui.chargecoin.ChargeCoinActivity
import fus.com.weathertoday.ui.webviewnews.news.NewActivity
import fus.com.weathertoday.utils.Preference

class ListWebViewFragment : BaseFragment() {

    companion object {
        fun newInstance() = ListWebViewFragment()
    }

    private lateinit var binding: FragmentListWebViewBinding
    private lateinit var viewModel: ListWebViewViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var preference: Preference? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ListWebViewViewModel::class.java]
        preference = Preference.buildInstance(requireContext())
        initAdapter()
        setTextHeader()

    }

    private fun setTextHeader() {
        val currentCoin = preference?.getValueCoin()
        binding.chargeTvCoin.text = currentCoin.toString()
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter {
            val dialogRemove: AlertDialog = AlertDialog.Builder(requireContext()).apply {
                setTitle("Mở ")
                setMessage("Bạn dùng 2 vàng để mở chứ ? ")
                setPositiveButton("Đồng ý") { dialog, id ->
                    preference?.apply {
                        if (getValueCoin() >= 2) {
                            setValueCoin(getValueCoin() - 2)

                            Toast.makeText(
                                requireContext(),
                                "Đã thêm  thành công và trù 2 vàng",
                                Toast.LENGTH_SHORT

                            ).show()
                            setTextHeader()
                            val intent = Intent(activity, NewActivity::class.java)
                            intent.putExtra("url", it)
                            startActivity(intent)
                        } else startActivity(
                            Intent(
                                requireContext(),
                                ChargeCoinActivity::class.java
                            )
                        )
                    }

                }
                setNegativeButton("Đóng") { dialog, id ->
                    dialog.dismiss()
                }
            }.create()
            dialogRemove.show()


        }
        binding.recyclerView.adapter = newsAdapter
        newsAdapter.setData(listData())
    }

    fun listData(): ArrayList<String> {
        val list: ArrayList<String> = arrayListOf()
        list.add("https://vnexpress.net/1-4-trieu-xe-ca-nhan-co-the-duoc-tu-dong-lui-chu-ky-kiem-dinh-4609960.html")
        list.add("https://vnexpress.net/dem-khong-ngu-cua-nguoi-viet-o-nhat-sau-vu-no-sung-dam-dao-4609894.html")
        list.add("https://vnexpress.net/dieu-tra-vu-phu-huynh-danh-co-giao-vi-con-hanh-kiem-trung-binh-4609886.html")
        list.add("https://vnexpress.net/vi-sao-vks-khang-nghi-dieu-tra-lai-vu-co-giao-bi-phat-5-nam-tu-4609909.html")
        list.add("https://vnexpress.net/phat-hien-bom-khi-dao-mong-xay-nha-4610029.html")
        list.add("https://vnexpress.net/thi-sinh-ky-thi-rieng-bach-khoa-ha-noi-tang-hon-30-4610190.html")
        list.add("https://vnexpress.net/nam-sinh-xuat-sac-cua-bach-khoa-ha-noi-hoc-gioi-hanh-hay-4609644.html")
        list.add("https://vnexpress.net/thu-khoa-dh-giao-thong-em-co-gang-de-bo-me-khong-phai-ban-ga-ban-lua-4600290.html")
        list.add("https://vnexpress.net/hon-3-000-hoc-sinh-dua-vao-lop-10-chuyen-o-tp-hcm-4609797.html")
        return list
    }


}