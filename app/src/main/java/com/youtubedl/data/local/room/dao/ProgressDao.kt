package com.youtubedl.data.local.room.dao

import android.arch.persistence.room.*
import com.youtubedl.data.local.room.entity.ProgressInfo
import io.reactivex.Flowable

/**
 * Created by cuongpm on 1/15/19.
 */

@Dao
interface ProgressDao {

    @Query("SELECT * FROM ProgressInfo")
    fun getProgressInfos(): Flowable<List<ProgressInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProgressInfo(progressInfo: ProgressInfo)

    @Delete
    fun deleteProgressInfo(progressInfo: ProgressInfo)
}