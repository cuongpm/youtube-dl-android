package com.youtubedl.data.local.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.youtubedl.data.local.room.entity.PageInfo
import com.youtubedl.data.local.room.entity.SupportedPage
import io.reactivex.Maybe

/**
 * Created by cuongpm on 12/8/18.
 */

@Dao
interface ConfigDao {

    @Query("SELECT * FROM PageInfo")
    fun getAllTopPages(): Maybe<List<PageInfo>>

    @Query("SELECT * FROM SupportedPage")
    fun getSupportedPages(): Maybe<List<SupportedPage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPage(pageInfo: PageInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSupportedPage(supportedPage: SupportedPage)
}