package com.youtubedl.ui.main.home

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.youtubedl.data.local.model.PageInfo
import com.youtubedl.ui.main.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

class BrowserViewModel @Inject constructor() : BaseViewModel() {

    val items: ObservableList<PageInfo> = ObservableArrayList()

    override fun start() {
        showTopPages()
    }

    override fun stop() {
    }

    private fun showTopPages() {
        val page1 = PageInfo(link = "http://youtube.com")
        val page2 = PageInfo(link = "http://facebook.com")
        val page3 = PageInfo(link = "http://twitter.com")
        val page4 = PageInfo(link = "http://instagram.com")
        val page5 = PageInfo(link = "http://dailymotion.com")
        val list = listOf(page1, page2, page3, page4, page5) as MutableList<PageInfo>
        with(items) {
            clear()
            addAll(list)
        }
    }
}