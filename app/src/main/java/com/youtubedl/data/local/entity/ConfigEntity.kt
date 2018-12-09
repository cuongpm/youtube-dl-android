package com.youtubedl.data.local.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by cuongpm on 12/8/18.
 */

@Entity(tableName = "Config")
data class ConfigEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = UUID.randomUUID().toString()
) {
    @SerializedName("is_update_app")
    @Expose
    var isUpdateApp: Boolean = false

    @SerializedName("app_version")
    @Expose
    var appVersion: String? = null

    @SerializedName("parser_server")
    @Expose
    var parserServer: String? = null

    @SerializedName("pages_supported")
    @Expose
    var pagesSupported: List<SupportedPage>? = null

    @SerializedName("pages_general")
    @Expose
    var pagesGeneral: List<String>? = null
}