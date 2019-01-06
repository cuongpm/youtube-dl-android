package com.youtubedl.data.local.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by cuongpm on 12/16/18.
 */

@Entity(tableName = "PageInfo")
data class PageInfo constructor(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    var name: String = "",

    @ColumnInfo(name = "link")
    @SerializedName("link")
    @Expose
    var link: String = "",

    @ColumnInfo(name = "icon")
    @SerializedName("icon")
    @Expose
    var icon: String = ""
)