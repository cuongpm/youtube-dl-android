package com.youtubedl.ui.component.binding

import android.databinding.BindingAdapter
import android.support.v4.view.ViewPager

/**
 * Created by cuongpm on 12/9/18.
 */

object ViewPagerBinding {

    @BindingAdapter("app:currentItem")
    @JvmStatic
    fun ViewPager.setCurrentItem(currentItem: Int) {
        setCurrentItem(currentItem, true)
    }

    @BindingAdapter("app:onPageChangeListener")
    @JvmStatic
    fun ViewPager.setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener) {
        clearOnPageChangeListeners()
        addOnPageChangeListener(onPageChangeListener)
    }
}