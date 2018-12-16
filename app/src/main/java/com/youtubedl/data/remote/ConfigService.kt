package com.youtubedl.data.remote

import com.youtubedl.data.local.room.ConfigEntity
import com.youtubedl.data.local.room.SupportedPage
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by cuongpm on 12/8/18.
 */

interface ConfigService {

    @GET("config.json")
    fun getconfig(): Observable<ConfigEntity>

    @GET("supported_pages.json")
    fun getSupportedPages(): Observable<List<SupportedPage>>
}