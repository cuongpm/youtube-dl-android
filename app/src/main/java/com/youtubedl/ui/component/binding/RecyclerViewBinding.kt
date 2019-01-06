package com.youtubedl.ui.component.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.youtubedl.data.local.room.entity.PageInfo
import com.youtubedl.ui.component.adapter.TopPageAdapter

/**
 * Created by cuongpm on 12/9/18.
 */

object RecyclerViewBinding {

    @BindingAdapter("app:items")
    @JvmStatic
    fun RecyclerView.setTopPages(items: List<PageInfo>) {
        with(adapter as TopPageAdapter) {
            setData(items)
        }
    }
}