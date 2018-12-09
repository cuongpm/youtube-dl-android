package com.youtubedl.ui.component.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.youtubedl.ui.main.home.BrowserFragment
import com.youtubedl.ui.main.progress.ProgressFragment
import com.youtubedl.ui.main.settings.SettingsFragment
import com.youtubedl.ui.main.video.VideoFragment

/**
 * Created by cuongpm on 12/9/18.
 */

class MainAdapter constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> BrowserFragment.newInstance()
            1 -> ProgressFragment.newInstance()
            2 -> VideoFragment.newInstance()
            3 -> VideoFragment.newInstance()
            else -> SettingsFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 5
    }

}