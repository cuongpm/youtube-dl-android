package com.youtubedl.util

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.youtubedl.data.local.room.entity.VideoInfo

/**
 * Created by cuongpm on 1/15/19.
 */

class RoomConverter {

    @TypeConverter
    fun convertJsonToVideo(json: String): VideoInfo {
        return Gson().fromJson(json, VideoInfo::class.java)
    }

    @TypeConverter
    fun convertListVideosToJson(video: VideoInfo): String {
        return Gson().toJson(video)
    }
}