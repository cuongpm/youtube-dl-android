package com.youtubedl.ui.main.player

import android.databinding.ObservableField
import com.youtubedl.ui.main.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by cuongpm on 1/6/19.
 */

class VideoPlayerViewModel @Inject constructor() : BaseViewModel() {

    val videoName = ObservableField("")
    val videoUrl = ObservableField("")
    val currentTime = ObservableField("")
    val totalTime = ObservableField("")

    override fun start() {
    }

    override fun stop() {
    }

    fun pressPrev() {

    }

    fun pressPauseOrPlay() {}

    fun pressNext() {}
}