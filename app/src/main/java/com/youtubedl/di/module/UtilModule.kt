package com.youtubedl.di.module

import com.youtubedl.util.FileUtil
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
    fun bindFileUtil() = FileUtil()
}