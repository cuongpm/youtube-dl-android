package com.youtubedl.ui.component.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.youtubedl.data.local.model.LocalVideo
import com.youtubedl.data.local.room.entity.ProgressInfo
import com.youtubedl.data.local.room.entity.PageInfo
import com.youtubedl.ui.component.adapter.ProgressAdapter
import com.youtubedl.ui.component.adapter.TopPageAdapter
import com.youtubedl.ui.component.adapter.VideoAdapter

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

    @BindingAdapter("app:items")
    @JvmStatic
    fun RecyclerView.setProgressInfos(items: List<ProgressInfo>) {
        with(adapter as ProgressAdapter) {
            setData(items)
        }
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun RecyclerView.setVideoInfos(items: List<LocalVideo>) {
        with(adapter as VideoAdapter) {
            setData(items)
        }
    }
}