package com.youtubedl.data.local.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by cuongpm on 12/8/18.
 */

@Entity(tableName = "SupportedPage")
data class SupportedPage constructor(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = UUID.randomUUID().toString()
) {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("pattern")
    @Expose
    var pattern: String? = null
}