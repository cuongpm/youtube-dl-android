package com.youtubedl.data.repository

import com.youtubedl.data.local.room.entity.ConfigEntity
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

    fun getConfig(): Flowable<ConfigEntity>

    fun getSupportedPages(): Flowable<List<SupportedPage>>

    fun saveConfig(configEntity: ConfigEntity)

    fun refreshConfig()
}

@Singleton
class ConfigRepositoryImpl @Inject constructor(
    @LocalData private val localDataSource: ConfigRepository,
    @RemoteData private val remoteDataSource: ConfigRepository
) : ConfigRepository {

    var cacheIsDirty = false

    var cachedConfig: ConfigEntity? = null

    override fun getSupportedPages(): Flowable<List<SupportedPage>> {
        return remoteDataSource.getSupportedPages()
    }

    override fun getConfig(): Flowable<ConfigEntity> {
        if (cachedConfig != null && !cacheIsDirty) {
            Flowable.just(cachedConfig)
        }

        val remoteConfig = getAndSaveRemoteConfig()

        return if (cacheIsDirty) remoteConfig else {
            val localConfig = getAndCacheLocalConfig()
            Flowable.concat(localConfig, remoteConfig).firstOrError().toFlowable()
        }
    }

    private fun getAndCacheLocalConfig(): Flowable<ConfigEntity> {
        return localDataSource.getConfig()
            .doOnNext { config -> cachedConfig = config }
    }

    private fun getAndSaveRemoteConfig(): Flowable<ConfigEntity> {
        return remoteDataSource.getConfig()
            .doOnNext { config ->
                localDataSource.saveConfig(config)
                cachedConfig = config
            }
            .doOnComplete { cacheIsDirty = false }
    }

    override fun saveConfig(configEntity: ConfigEntity) {
        remoteDataSource.saveConfig(configEntity)
        localDataSource.saveConfig(configEntity)
        cachedConfig = configEntity
    }

    override fun refreshConfig() {
        cacheIsDirty = true
    }
}