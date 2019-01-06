package com.youtubedl.di.module

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.youtubedl.data.remote.service.ConfigService
import com.youtubedl.data.remote.service.VideoService
import com.youtubedl.util.Memory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by cuongpm on 12/6/18.
 */

@Module
class NetworkModule {

    companion object {
        private const val DATA_URL = "https://generaldata-79d9b.firebaseapp.com/youtube-dl/"
        private const val YTDL_URL = "http://youtube-dl55.herokuapp.com/api/"
    }

    private fun buildOkHttpClient(application: Application): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .connectTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .cache(
                Cache(
                    File(application.cacheDir, "YoutubeDLCache"),
                    Memory.calcCacheSize(application, .25f)
                )
            )
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application): OkHttpClient = buildOkHttpClient(application)

    @Provides
    @Singleton
    fun provideConfigService(okHttpClient: OkHttpClient): ConfigService = Retrofit.Builder()
        .baseUrl(DATA_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ConfigService::class.java)

    @Provides
    @Singleton
    fun provideVideoService(okHttpClient: OkHttpClient): VideoService = Retrofit.Builder()
        .baseUrl(YTDL_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(VideoService::class.java)
}
