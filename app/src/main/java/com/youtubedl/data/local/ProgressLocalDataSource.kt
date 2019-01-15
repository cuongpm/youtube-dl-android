package com.youtubedl.data.local

import com.youtubedl.data.local.room.dao.ProgressDao
import com.youtubedl.data.local.room.entity.ProgressInfo
import com.youtubedl.data.repository.ProgressRepository
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 1/15/19.
 */

@Singleton
class ProgressLocalDataSource @Inject constructor(
    private val progressDao: ProgressDao
) : ProgressRepository {

    override fun getProgressInfos(): Flowable<List<ProgressInfo>> {
        return progressDao.getProgressInfos()
    }

    override fun saveProgressInfo(progressInfo: ProgressInfo) {
        progressDao.insertProgressInfo(progressInfo)
    }

    override fun deleteProgressInfo(progressInfo: ProgressInfo) {
        progressDao.deleteProgressInfo(progressInfo)
    }
}