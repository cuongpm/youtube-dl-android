package com.youtubedl.util.fragment

import android.support.v4.app.Fragment

/**
 * Created by cuongpm on 2/13/19.
 */

class StubbedFragmentFactory : FragmentFactory {
    override fun createBrowserFragment() = Fragment()

    override fun createProgressFragment() = Fragment()

    override fun createVideoFragment() = Fragment()

    override fun createSettingsFragment() = Fragment()
}