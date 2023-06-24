package fus.com.weathertoday.ui.container

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import fus.com.weathertoday.R
import fus.com.weathertoday.databinding.FragmentContainerBinding
import fus.com.weathertoday.ui.BaseFragment
import fus.com.weathertoday.ui.ViewPager
import fus.com.weathertoday.ui.home.HomeFragment
import fus.com.weathertoday.ui.webviewnews.list_webview.ListWebViewFragment

@AndroidEntryPoint
class ContainerFragment : BaseFragment() {

    private lateinit var binding: FragmentContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContainerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = arrayListOf<Fragment>(
            HomeFragment(),
            ListWebViewFragment()
        )
        val adapter = ViewPager(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewpager.adapter = adapter
        binding.viewpager.offscreenPageLimit = 2
        binding.viewpager.isUserInputEnabled = false
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

    companion object {
        @JvmStatic
        fun newInstance() = ContainerFragment()
    }
}