package com.youtubedl.data.local.room.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by cuongpm on 12/16/18.
 */

data class PageInfo constructor(
    @SerializedName("name")
    @Expose
    var name: String = "",
    @SerializedName("link")
    @Expose
    var link: String = "",
    @SerializedName("icon")
    @Expose
    var icon: String = ""
)