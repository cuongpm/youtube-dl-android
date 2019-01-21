package com.youtubedl.ui.main.video

import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.youtubedl.data.local.model.LocalVideo
import com.youtubedl.ui.main.base.BaseViewModel
import com.youtubedl.util.FileUtil
import com.youtubedl.util.SingleLiveEvent
import com.youtubedl.util.ext.deleteMedia
import com.youtubedl.util.ext.scanMedia
import java.io.File
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

class VideoViewModel @Inject constructor(
    private val fileUtil: FileUtil
) : BaseViewModel() {

    companion object {
        const val FILE_EXIST_ERROR_CODE = 1
        const val FILE_INVALID_ERROR_CODE = 2
    }

    val localVideos: ObservableList<LocalVideo> = ObservableArrayList()
    val renameErrorEvent = SingleLiveEvent<Int>()

    override fun start() {
        getListDownloadedVideos()
    }

    override fun stop() {
    }

    private fun getListDownloadedVideos() {
        val listVideos: MutableList<LocalVideo> = mutableListOf()
        fileUtil.listFiles.map { file -> listVideos.add(LocalVideo(file)) }
        with(localVideos) {
            clear()
            addAll(listVideos)
        }
    }

    fun deleteVideo(file: File) {
        if (file.exists()) {
            localVideos.find { it.file.path == file.path }?.let {
                localVideos.remove(it)
                file.delete()
            }
        }
    }

    fun renameVideo(context: Context, file: File, newName: String) {
        if (newName.isNotEmpty()) {
            val newFile = File(file.parent, newName + file.extension)
            if (newFile.exists()) {
                renameErrorEvent.value = FILE_EXIST_ERROR_CODE
            } else if (file.renameTo(newFile)) {
                file.deleteMedia(context)
                newFile.scanMedia(context)
                localVideos.find { it.file.path == file.path }?.let {
                    it.file = newFile
                    localVideos[localVideos.indexOf(it)] = it
                }
            }
        } else {
            renameErrorEvent.value = FILE_INVALID_ERROR_CODE
        }
    }
}