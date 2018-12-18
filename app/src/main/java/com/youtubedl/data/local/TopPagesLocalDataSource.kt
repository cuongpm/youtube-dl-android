package com.youtubedl.data.local

import com.youtubedl.data.local.room.entity.PageInfo
import com.youtubedl.data.repository.TopPagesRepository
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 12/18/18.
 */

@Singleton
class TopPagesLocalDataSource @Inject constructor(

) : TopPagesRepository {

    override fun getTopPages(): Flowable<List<PageInfo>> {
        return Flowable.empty()
    }
}