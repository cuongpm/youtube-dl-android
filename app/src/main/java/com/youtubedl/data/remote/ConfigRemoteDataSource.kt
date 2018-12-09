package com.youtubedl.data.remote

import com.youtubedl.data.local.entity.ConfigEntity
import com.youtubedl.data.local.entity.SupportedPage
import com.youtubedl.data.repository.ConfigDataSource
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 12/8/18.
 */

@Singleton
class ConfigRemoteDataSource @Inject constructor() : ConfigDataSource() {

    override fun getSupportedPages(): Flowable<List<SupportedPage>> {
        return Flowable.empty()
    }

    override fun getConfig(): Flowable<ConfigEntity> {
        return Flowable.empty()
    }

    override fun saveConfig(configEntity: ConfigEntity) {
    }
}