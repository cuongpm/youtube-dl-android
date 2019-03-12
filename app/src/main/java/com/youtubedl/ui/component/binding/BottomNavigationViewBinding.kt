package com.youtubedl.ui.component.binding

import android.databinding.BindingAdapter
import android.support.design.widget.BottomNavigationView
import com.youtubedl.R

/**
 * Created by cuongpm on 12/15/18.
 */

object BottomNavigationViewBinding {

    @BindingAdapter("app:selectedItemId")
    @JvmStatic
    fun BottomNavigationView.setSelectedItemId(position: Int) {
        selectedItemId = when (position) {
            0 -> R.id.tab_browser
            1 -> R.id.tab_progress
            2 -> R.id.tab_video
            else -> R.id.tab_settings
        }
    }
}