package com.youtubedl.util.fragment

import android.support.v4.app.Fragment
import com.youtubedl.ui.main.home.BrowserFragment
import com.youtubedl.ui.main.progress.ProgressFragment
import com.youtubedl.ui.main.settings.SettingsFragment
import com.youtubedl.ui.main.video.VideoFragment
import javax.inject.Inject

/**
 * Created by cuongpm on 2/13/19.
 */

interface FragmentFactory {
    fun createBrowserFragment(): Fragment
    fun createProgressFragment(): Fragment
    fun createVideoFragment(): Fragment
    fun createSettingsFragment(): Fragment
}

class FragmentFactoryImpl @Inject constructor() : FragmentFactory {
    override fun createBrowserFragment() = BrowserFragment.newInstance()

    override fun createProgressFragment() = ProgressFragment.newInstance()

    override fun createVideoFragment() = VideoFragment.newInstance()

    override fun createSettingsFragment() = SettingsFragment.newInstance()
}