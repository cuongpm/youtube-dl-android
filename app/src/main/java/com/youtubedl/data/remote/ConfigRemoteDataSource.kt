package com.youtubedl.data.remote

import com.youtubedl.data.local.room.entity.SupportedPage
import com.youtubedl.data.remote.service.ConfigService
import com.youtubedl.data.repository.ConfigRepository
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 12/8/18.
 */

@Singleton
class ConfigRemoteDataSource @Inject constructor(
    private val configService: ConfigService
) : ConfigRepository {

    override fun getSupportedPages(): Flowable<List<SupportedPage>> {
        return configService.getSupportedPages()
    }

    override fun saveSupportedPages(supportedPages: List<SupportedPage>) {
    }

    override fun refreshConfig() {
    }
}