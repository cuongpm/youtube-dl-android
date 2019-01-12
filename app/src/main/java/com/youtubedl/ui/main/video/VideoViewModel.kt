package com.youtubedl.ui.main.video

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.youtubedl.data.local.model.LocalVideo
import com.youtubedl.ui.main.base.BaseViewModel
import com.youtubedl.util.FileUtil.listFiles
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

class VideoViewModel @Inject constructor() : BaseViewModel() {

    val localVideos: ObservableList<LocalVideo> = ObservableArrayList()

    override fun start() {
        getListDownloadedVideos()
    }

    override fun stop() {
    }

    private fun getListDownloadedVideos() {
        val listVideos: MutableList<LocalVideo> = mutableListOf()
        listFiles.map { file -> listVideos.add(LocalVideo(file)) }
        with(localVideos) {
            clear()
            addAll(listVideos)
        }
    }
}