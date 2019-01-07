package com.youtubedl.data.local

import com.youtubedl.data.local.room.dao.VideoDao
import com.youtubedl.data.local.room.entity.VideoInfo
import com.youtubedl.data.repository.VideoRepository
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 1/6/19.
 */

@Singleton
class VideoLocalDataSource @Inject constructor(
    private val videoDao: VideoDao
) : VideoRepository {

    override fun getVideoInfo(url: String): Flowable<VideoInfo> {
        return videoDao.getVideoById(url).toFlowable()
    }

    override fun saveVideoInfo(videoInfo: VideoInfo) {
        videoDao.insertVideo(videoInfo)
    }

}