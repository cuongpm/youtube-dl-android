package com.youtubedl.di.module

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import com.youtubedl.util.FileUtil
import com.youtubedl.util.IntentUtil
import com.youtubedl.util.SystemUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by cuongpm on 1/13/19.
 */

@Module
class UtilModule {

    @Singleton
    @Provides
    fun bindDownloadManager(application: Application): DownloadManager =
        application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    @Singleton
    @Provides
    fun bindFileUtil() = FileUtil()

    @Singleton
    @Provides
    fun bindSystemUtil() = SystemUtil()

    @Singleton
    @Provides
    fun bindIntentUtil() = IntentUtil()
}