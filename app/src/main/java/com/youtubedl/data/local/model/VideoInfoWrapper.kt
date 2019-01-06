package com.youtubedl.data.local.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.youtubedl.data.local.room.entity.VideoInfo

/**
 * Created by cuongpm on 1/6/19.
 */

data class VideoInfoWrapper constructor(
    @SerializedName("info")
    @Expose
    var videoInfo: VideoInfo?
)