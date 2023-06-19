package fus.com.weathertoday.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import fus.com.weathertoday.ui.home.HomeFragment
import fus.com.weathertoday.ui.webviewnews.list_webview.ListWebViewFragment

class ViewPager(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> ListWebViewFragment()
            else -> HomeFragment()
        }
    }

}