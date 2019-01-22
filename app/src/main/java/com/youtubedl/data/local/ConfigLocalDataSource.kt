package com.youtubedl.data.local

import com.youtubedl.data.local.room.dao.ConfigDao
import com.youtubedl.data.local.room.entity.SupportedPage
import com.youtubedl.data.repository.ConfigRepository
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 12/8/18.
 */

@Singleton
class ConfigLocalDataSource @Inject constructor(
    private val configDao: ConfigDao
) : ConfigRepository {

    override fun getSupportedPages(): Flowable<List<SupportedPage>> {
        return configDao.getSupportedPages().toFlowable()
    }

    override fun saveSupportedPages(supportedPages: List<SupportedPage>) {
        supportedPages.map { configDao.insertSupportedPage(it) }
    }
}