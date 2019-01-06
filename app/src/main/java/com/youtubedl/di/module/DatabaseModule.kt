package com.youtubedl.di.module

import android.arch.persistence.room.Room
import com.youtubedl.DLApplication
import com.youtubedl.data.local.room.AppDatabase
import com.youtubedl.data.local.room.dao.ConfigDao
import com.youtubedl.data.local.room.dao.VideoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by cuongpm on 12/8/18.
 */

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: DLApplication): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "dl.db").build()
    }

    @Singleton
    @Provides
    fun provideConfigDao(database: AppDatabase): ConfigDao = database.configDao()

    @Singleton
    @Provides
    fun provideCommentDao(database: AppDatabase): VideoDao = database.videoDao()
}