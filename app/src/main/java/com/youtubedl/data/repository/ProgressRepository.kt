package com.youtubedl.data.repository

import com.youtubedl.data.local.room.entity.ProgressInfo
import com.youtubedl.di.qualifier.LocalData
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 1/15/19.
 */

interface ProgressRepository {

    fun getProgressInfos(): Flowable<List<ProgressInfo>>

    fun saveProgressInfo(progressInfo: ProgressInfo)

    fun deleteProgressInfo(progressInfo: ProgressInfo)
}

@Singleton
class ProgressRepositoryImpl @Inject constructor(
    @LocalData private val localDataSource: ProgressRepository
) : ProgressRepository {

    override fun getProgressInfos(): Flowable<List<ProgressInfo>> {
        return localDataSource.getProgressInfos()
    }

    override fun saveProgressInfo(progressInfo: ProgressInfo) {
        localDataSource.saveProgressInfo(progressInfo)
    }

    override fun deleteProgressInfo(progressInfo: ProgressInfo) {
        localDataSource.deleteProgressInfo(progressInfo)
    }
}