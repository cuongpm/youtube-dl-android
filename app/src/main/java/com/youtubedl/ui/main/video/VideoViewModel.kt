package com.youtubedl.ui.main.video

import android.databinding.ObservableField
import com.youtubedl.ui.main.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

class VideoViewModel @Inject constructor() : BaseViewModel() {

    val videoName = ObservableField("")

    override fun start() {
    }

    override fun stop() {
    }
}