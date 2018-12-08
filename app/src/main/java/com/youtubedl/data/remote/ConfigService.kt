package com.youtubedl.data.remote

import com.youtubedl.data.local.entity.ConfigEntity
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by cuongpm on 12/8/18.
 */

interface ConfigService {

    @GET("config.json")
    fun getconfigData(): Observable<ConfigEntity>
}