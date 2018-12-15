package com.youtubedl.ui.component.binding

import android.databinding.BindingAdapter
import com.roughike.bottombar.BottomBar
import com.roughike.bottombar.OnTabSelectListener

/**
 * Created by cuongpm on 12/15/18.
 */

object BottomBarBinding {

    @BindingAdapter("app:defaultTabPosition")
    @JvmStatic
    fun setDefaultTabPosition(bottomBar: BottomBar, defaultTabPosition: Int) {
        bottomBar.setDefaultTabPosition(defaultTabPosition)
    }

    @BindingAdapter("app:onTabSelectListener")
    @JvmStatic
    fun setOnTabSelectListener(bottomBar: BottomBar, onTabSelectListener: OnTabSelectListener) {
        bottomBar.removeOnTabSelectListener()
        bottomBar.setOnTabSelectListener(onTabSelectListener)
    }
}