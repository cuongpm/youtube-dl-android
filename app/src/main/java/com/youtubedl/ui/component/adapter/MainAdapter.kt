package com.youtubedl.ui.component.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.youtubedl.util.fragment.FragmentFactory

/**
 * Created by cuongpm on 12/9/18.
 */

class MainAdapter constructor(
    fm: FragmentManager,
    private val fragmentFactory: FragmentFactory
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragmentFactory.createBrowserFragment()
            1 -> fragmentFactory.createProgressFragment()
            2 -> fragmentFactory.createVideoFragment()
            else -> fragmentFactory.createSettingsFragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }
}