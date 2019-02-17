package com.youtubedl.ui.component.binding

import android.databinding.BindingAdapter
import android.net.Uri
import android.support.v4.content.FileProvider
import android.widget.VideoView
import java.io.File

/**
 * Created by cuongpm on 12/9/18.
 */

object VideoViewBinding {

    @BindingAdapter("app:videoURI")
    @JvmStatic
    fun VideoView.setVideoURI(videoPath: String?) {
        videoPath?.let { path ->
            val uri = if (path.startsWith("http")) {
                Uri.parse(path)
            } else {
                FileProvider.getUriForFile(context, context.packageName + ".provider", File(path))
            }
            setVideoURI(uri)
        }
    }
}