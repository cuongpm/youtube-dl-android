package com.youtubedl.di.module

import com.youtubedl.data.local.ConfigLocalDataSource
import com.youtubedl.data.local.TopPagesLocalDataSource
import com.youtubedl.data.remote.ConfigRemoteDataSource
import com.youtubedl.data.remote.TopPagesRemoteDataSource
import com.youtubedl.data.repository.ConfigRepository
import com.youtubedl.data.repository.ConfigRepositoryImpl
import com.youtubedl.data.repository.TopPagesRepository
import com.youtubedl.data.repository.TopPagesRepositoryImpl
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
}