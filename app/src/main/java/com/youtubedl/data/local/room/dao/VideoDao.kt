package com.youtubedl.data.local.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.youtubedl.data.local.room.entity.VideoInfo
import io.reactivex.Maybe

/**
 * Created by cuongpm on 1/6/19.
 */

@Dao
interface VideoDao {

    @Query("SELECT * FROM VideoInfo WHERE originalUrl = :url")
    fun getVideoById(url: String): Maybe<VideoInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideo(videoInfo: VideoInfo)
}