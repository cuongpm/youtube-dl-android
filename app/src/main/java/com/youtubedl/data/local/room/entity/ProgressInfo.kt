package com.youtubedl.data.local.room.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.youtubedl.util.RoomConverter
import com.youtubedl.util.ext.getFileSize
import java.util.*

/**
 * Created by cuongpm on 1/8/19.
 */

@Entity(tableName = "ProgressInfo")
@TypeConverters(RoomConverter::class)
data class ProgressInfo constructor(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),

    var downloadId: Long = 0,

    @TypeConverters(RoomConverter::class)
    var videoInfo: VideoInfo,

    var bytesDownloaded: Int = 0,

    var bytesTotal: Int = 0
) {

    var progress: Int = 0
        get() = (bytesDownloaded * 100f / bytesTotal).toInt()

    var progressSize: String = ""
        get() = getFileSize(bytesDownloaded.toDouble()) + "/" + getFileSize(bytesTotal.toDouble())

}