package com.youtubedl.data.remote

import com.youtubedl.data.local.room.entity.VideoInfo
import com.youtubedl.data.remote.service.VideoService
import com.youtubedl.data.repository.VideoRepository
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 1/6/19.
 */

@Singleton
class VideoRemoteDataSource @Inject constructor(
    private val videoService: VideoService
) : VideoRepository {

    override fun getVideoInfo(url: String): Flowable<VideoInfo> {
        return videoService.getVideoInfo(url)
            .flatMap { videoInfoWrapper -> Flowable.just(videoInfoWrapper.videoInfo) }
    }

    override fun saveVideoInfo(videoInfo: VideoInfo) {
    }
}