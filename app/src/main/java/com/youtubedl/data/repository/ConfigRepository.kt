package com.youtubedl.data.repository

import com.youtubedl.data.local.entity.ConfigEntity
import com.youtubedl.data.local.entity.SupportedPage
import com.youtubedl.di.qualifier.LocalData
import com.youtubedl.di.qualifier.RemoteData
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 12/8/18.
 */

@Singleton
class ConfigRepository @Inject constructor(
    @LocalData private val localDataSource: ConfigDataSource,
    @RemoteData private val remoteDataSource: ConfigDataSource
) : ConfigDataSource() {

    var cacheIsDirty = false

    var cachedConfig: ConfigEntity? = null

    override fun getSupportedPages(): Flowable<List<SupportedPage>> {
        return Flowable.empty()
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