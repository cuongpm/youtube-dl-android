package com.youtubedl.data.local.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.youtubedl.data.local.room.dao.ConfigDao
import com.youtubedl.data.local.room.dao.VideoDao
import com.youtubedl.data.local.room.entity.ConfigEntity
import com.youtubedl.data.local.room.entity.PageInfo
import com.youtubedl.data.local.room.entity.SupportedPage
import com.youtubedl.data.local.room.entity.VideoInfo

/**
 * Created by cuongpm on 12/8/18.
 */

@Database(entities = [ConfigEntity::class, PageInfo::class, SupportedPage::class, VideoInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun configDao(): ConfigDao

    abstract fun videoDao(): VideoDao
}