package com.youtubedl.data.remote

import com.youtubedl.data.local.room.entity.PageInfo
import com.youtubedl.data.remote.service.ConfigService
import com.youtubedl.data.repository.TopPagesRepository
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 12/18/18.
 */

@Singleton
class TopPagesRemoteDataSource @Inject constructor(
    private val configService: ConfigService
) : TopPagesRepository {

    override fun getTopPages(): Flowable<List<PageInfo>> {
        return configService.getTopPages()
    }
}