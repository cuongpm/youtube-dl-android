package com.youtubedl.data.repository

import com.youtubedl.data.local.entity.ConfigEntity
import com.youtubedl.data.local.entity.SupportedPage
import io.reactivex.Flowable

/**
 * Created by cuongpm on 12/8/18.
 */

abstract class ConfigDataSource {

    abstract fun getConfig(): Flowable<ConfigEntity>

    abstract fun getSupportedPages(): Flowable<List<SupportedPage>>

    open fun saveConfig(configEntity: ConfigEntity) {}

    open fun refreshConfig() {}


}