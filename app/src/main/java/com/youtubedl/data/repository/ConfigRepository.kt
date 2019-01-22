package com.youtubedl.data.repository

import com.youtubedl.data.local.room.entity.SupportedPage
import com.youtubedl.di.qualifier.LocalData
import com.youtubedl.di.qualifier.RemoteData
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 12/8/18.
 */

interface ConfigRepository {

    fun getSupportedPages(): Flowable<List<SupportedPage>>

    fun saveSupportedPages(supportedPages: List<SupportedPage>)

    fun refreshConfig()
}

@Singleton
class ConfigRepositoryImpl @Inject constructor(
    @LocalData private val localDataSource: ConfigRepository,
    @RemoteData private val remoteDataSource: ConfigRepository
) : ConfigRepository {

    var cacheIsDirty = false

//    var cachedConfig: ConfigEntity? = null

    override fun getSupportedPages(): Flowable<List<SupportedPage>> {
        return remoteDataSource.getSupportedPages()
    }

    override fun saveSupportedPages(supportedPages: List<SupportedPage>) {
    }

    override fun refreshConfig() {
        cacheIsDirty = true
    }
}