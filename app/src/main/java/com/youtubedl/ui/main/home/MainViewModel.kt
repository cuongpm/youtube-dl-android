package com.youtubedl.ui.main.home

import android.databinding.ObservableField
import com.youtubedl.OpenForTesting
import com.youtubedl.data.local.room.entity.VideoInfo
import com.youtubedl.ui.main.base.BaseViewModel
import com.youtubedl.util.SingleLiveEvent
import javax.inject.Inject

/**
 * Created by cuongpm on 12/9/18.
 */

@OpenForTesting
class MainViewModel @Inject constructor() : BaseViewModel() {

    val currentItem = ObservableField<Int>()

    val pressBackBtnEvent = SingleLiveEvent<Void>()

    val downloadVideoEvent = SingleLiveEvent<VideoInfo>()

    override fun start() {
    }

    override fun stop() {
    }
}