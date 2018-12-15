package com.youtubedl.ui.component.binding

import android.databinding.BindingAdapter
import android.support.v4.view.ViewPager

/**
 * Created by cuongpm on 12/9/18.
 */

object ViewPagerBinding {

    @BindingAdapter("app:currentItem")
    @JvmStatic
    fun setCurrentItem(viewPager: ViewPager, currentItem: Int) {
        viewPager.setCurrentItem(currentItem, true)
    }

    @BindingAdapter("app:onPageChangeListener")
    @JvmStatic
    fun setOnPageChangeListener(viewPager: ViewPager, onPageChangeListener: ViewPager.OnPageChangeListener) {
        viewPager.clearOnPageChangeListeners()
        viewPager.addOnPageChangeListener(onPageChangeListener)
    }
}