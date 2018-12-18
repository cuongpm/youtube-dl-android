package com.youtubedl.data.repository

import com.youtubedl.data.local.room.entity.PageInfo
import com.youtubedl.di.qualifier.LocalData
import com.youtubedl.di.qualifier.RemoteData
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 12/18/18.
 */

interface TopPagesRepository {
    fun getTopPages(): Flowable<List<PageInfo>>
}

@Singleton
class TopPagesRepositoryImpl @Inject constructor(
    @LocalData private val localDataSource: TopPagesRepository,
    @RemoteData private val remoteDataSource: TopPagesRepository
) : TopPagesRepository {

    override fun getTopPages(): Flowable<List<PageInfo>> {
        return remoteDataSource.getTopPages()
    }
}