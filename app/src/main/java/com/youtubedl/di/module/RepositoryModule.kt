package com.youtubedl.di.module

import com.youtubedl.data.local.ConfigLocalDataSource
import com.youtubedl.data.remote.ConfigRemoteDataSource
import com.youtubedl.data.repository.ConfigDataSource
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
    abstract fun bindConfigLocalDataSource(localDataSource: ConfigLocalDataSource): ConfigDataSource

    @Singleton
    @Binds
    @RemoteData
    abstract fun bindConfigRemoteDataSource(remoteDataSource: ConfigRemoteDataSource): ConfigDataSource
}