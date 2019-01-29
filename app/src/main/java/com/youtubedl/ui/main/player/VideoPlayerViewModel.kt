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

    var isVolumeOn = true

    override fun start() {
    }

    override fun stop() {
    }

    fun pressPrev() {

    }

    fun pressPauseOrPlay() {}

    fun pressNext() {}

    fun getVolume(): Float {
        val amount = if (isVolumeOn) 100 else 0
        val max = 100.0
        val numerator: Double = if (max - amount > 0) Math.log((max - amount)) else 0.0
        return (1 - numerator / Math.log(max)).toFloat()
    }
}