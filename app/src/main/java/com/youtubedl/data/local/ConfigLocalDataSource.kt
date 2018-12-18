package com.youtubedl.data.local

import com.youtubedl.data.local.room.entity.ConfigEntity
import com.youtubedl.data.local.room.entity.SupportedPage
import com.youtubedl.data.repository.ConfigRepository
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 12/8/18.
 */

@Singleton
class ConfigLocalDataSource @Inject constructor() : ConfigRepository {

    override fun getSupportedPages(): Flowable<List<SupportedPage>> {
        return Flowable.empty()
    }

    override fun getConfig(): Flowable<ConfigEntity> {
        return Flowable.empty()
    }

    override fun saveConfig(configEntity: ConfigEntity) {}

    override fun refreshConfig() {
    }
}