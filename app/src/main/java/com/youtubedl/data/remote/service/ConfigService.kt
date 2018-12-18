package com.youtubedl.data.remote.service

import com.youtubedl.data.local.room.entity.PageInfo
import com.youtubedl.data.local.room.entity.ConfigEntity
import com.youtubedl.data.local.room.entity.SupportedPage
import io.reactivex.Flowable
import retrofit2.http.GET

/**
 * Created by cuongpm on 12/8/18.
 */

interface ConfigService {

    @GET("config.json")
    fun getconfig(): Flowable<ConfigEntity>

    @GET("supported_pages.json")
    fun getSupportedPages(): Flowable<List<SupportedPage>>

    @GET("top_pages.json")
    fun getTopPages(): Flowable<List<PageInfo>>

}