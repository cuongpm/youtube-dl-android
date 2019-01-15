package com.youtubedl.di.module

import com.youtubedl.data.local.ConfigLocalDataSource
import com.youtubedl.data.local.ProgressLocalDataSource
import com.youtubedl.data.local.TopPagesLocalDataSource
import com.youtubedl.data.local.VideoLocalDataSource
import com.youtubedl.data.remote.ConfigRemoteDataSource
import com.youtubedl.data.remote.TopPagesRemoteDataSource
import com.youtubedl.data.remote.VideoRemoteDataSource
import com.youtubedl.data.repository.*
import com.youtubedl.di.qualifier.LocalData
import com.youtubedl.di.qualifier.RemoteData
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Created by cuongpm on 12/6/18.
 */

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    @LocalData
    abstract fun bindConfigLocalDataSource(localDataSource: ConfigLocalDataSource): ConfigRepository

    @Singleton
    @Binds
    @RemoteData
    abstract fun bindConfigRemoteDataSource(remoteDataSource: ConfigRemoteDataSource): ConfigRepository

    @Singleton
    @Binds
    abstract fun bindConfigRepositoryImpl(configRepository: ConfigRepositoryImpl): ConfigRepository

    @Singleton
    @Binds
    @LocalData
    abstract fun bindTopPagesLocalDataSource(localDataSource: TopPagesLocalDataSource): TopPagesRepository

    @Singleton
    @Binds
    @RemoteData
    abstract fun bindTopPagesRemoteDataSource(remoteDataSource: TopPagesRemoteDataSource): TopPagesRepository

    @Singleton
    @Binds
    abstract fun bindTopPagesRepositoryImpl(topPagesRepository: TopPagesRepositoryImpl): TopPagesRepository

    @Singleton
    @Binds
    @LocalData
    abstract fun bindVideoLocalDataSource(localDataSource: VideoLocalDataSource): VideoRepository

    @Singleton
    @Binds
    @RemoteData
    abstract fun bindVideoRemoteDataSource(remoteDataSource: VideoRemoteDataSource): VideoRepository

    @Singleton
    @Binds
    abstract fun bindVideoRepositoryImpl(videoRepository: VideoRepositoryImpl): VideoRepository

    @Singleton
    @Binds
    @LocalData
    abstract fun bindProgressLocalDataSource(localDataSource: ProgressLocalDataSource): ProgressRepository

    @Singleton
    @Binds
    abstract fun bindProgressRepositoryImpl(progressRepository: ProgressRepositoryImpl): ProgressRepository
}