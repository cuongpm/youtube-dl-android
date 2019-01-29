package com.youtubedl.ui.component.binding

import android.databinding.BindingAdapter
import android.media.MediaPlayer
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
    fun VideoView.setVideoURI(videoPath: String) {
        val uri = if (videoPath.startsWith("http")) {
            Uri.parse(videoPath)
        } else {
            FileProvider.getUriForFile(context, context.packageName + ".provider", File(videoPath))
        }
        setVideoURI(uri)
    }

    @BindingAdapter("app:onPreparedListener")
    @JvmStatic
    fun VideoView.setOnPreparedListener(onPreparedListener: MediaPlayer.OnPreparedListener?) {
        setOnPreparedListener(onPreparedListener)
    }
}