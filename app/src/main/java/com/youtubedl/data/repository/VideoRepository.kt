package com.youtubedl.data.repository

import com.youtubedl.data.local.room.entity.VideoInfo
import com.youtubedl.di.qualifier.LocalData
import com.youtubedl.di.qualifier.RemoteData
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 1/6/19.
 */

interface VideoRepository {

    fun getVideoInfo(url: String): Flowable<VideoInfo>

    fun saveVideoInfo(videoInfo: VideoInfo)
}

@Singleton
class VideoRepositoryImpl @Inject constructor(
    @LocalData private val localDataSource: VideoRepository,
    @RemoteData private val remoteDataSource: VideoRepository
) : VideoRepository {

    var cachedVideo: VideoInfo? = null

    override fun getVideoInfo(url: String): Flowable<VideoInfo> {
        cachedVideo?.let { Flowable.just(it) }

        val localConfig = getAndCacheLocalVideo(url)
        val remoteConfig = getAndSaveRemoteVideo(url)
//        return Flowable.concat(localConfig, remoteConfig).firstOrError().toFlowable()

        return remoteConfig
    }

    override fun saveVideoInfo(videoInfo: VideoInfo) {
        remoteDataSource.saveVideoInfo(videoInfo)
        localDataSource.saveVideoInfo(videoInfo)
        cachedVideo = videoInfo
    }

    private fun getAndCacheLocalVideo(url: String): Flowable<VideoInfo> {
        return localDataSource.getVideoInfo(url)
            .doOnNext { videoInfo -> cachedVideo = videoInfo }
    }

    private fun getAndSaveRemoteVideo(url: String): Flowable<VideoInfo> {
        return remoteDataSource.getVideoInfo(url)
            .doOnNext { videoInfo ->
                localDataSource.saveVideoInfo(videoInfo)
                cachedVideo = videoInfo
            }
    }
}