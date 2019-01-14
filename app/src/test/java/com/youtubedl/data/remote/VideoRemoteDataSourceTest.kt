package com.youtubedl.data.remote

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.youtubedl.data.local.model.VideoInfoWrapper
import com.youtubedl.data.local.room.entity.VideoInfo
import com.youtubedl.data.remote.service.VideoService
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

/**
 * Created by cuongpm on 1/14/19.
 */

class VideoRemoteDataSourceTest {

    private lateinit var videoService: VideoService

    private lateinit var videoRemoteDataSource: VideoRemoteDataSource

    private lateinit var videoInfoWrapper: VideoInfoWrapper

    private lateinit var videoInfo: VideoInfo

    private lateinit var url: String

    @Before
    fun setup() {
        videoService = mock()
        videoRemoteDataSource = VideoRemoteDataSource(videoService)

        url = "videoUrl"
        videoInfo = VideoInfo(title = "title", originalUrl = "originalUrl")
        videoInfoWrapper = VideoInfoWrapper(videoInfo)
    }

    @Test
    fun `test get video info`() {
        doReturn(Flowable.just(videoInfoWrapper)).`when`(videoService).getVideoInfo(url)

        videoRemoteDataSource.getVideoInfo(url).test()
            .assertNoErrors()
            .assertValue { it == videoInfo }
    }
}