package com.youtubedl.data.remote.service

import com.youtubedl.data.local.model.VideoInfoWrapper
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by cuongpm on 1/6/19.
 */

interface VideoService {

    @GET("info")
    fun getVideoInfo(@Query("url") url: String): Flowable<VideoInfoWrapper>
}