package com.youtubedl.data.local.model

import com.youtubedl.data.local.room.entity.VideoInfo
import com.youtubedl.util.FileUtil.getFileSize

/**
 * Created by cuongpm on 1/8/19.
 */

data class ProgressInfo constructor(
    var downloadId: Long = 0,

    var videoInfo: VideoInfo,

    var bytesDownloaded: Int = 0,

    var bytesTotal: Int = 0
) {

    var progress: Int = 0
        get() = (bytesDownloaded * 100f / bytesTotal).toInt()

    var progressSize: String = ""
        get() = getFileSize(bytesDownloaded.toDouble()) + "/" + getFileSize(bytesTotal.toDouble())

}