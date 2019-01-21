package com.youtubedl.util.ext

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.text.DecimalFormat

/**
 * Created by cuongpm on 1/9/19.
 */

fun File?.scanMedia(context: Context) {
    this?.let {
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        intent.data = Uri.fromFile(it)
        context.sendBroadcast(intent)
    }
}

fun File?.deleteMedia(context: Context) {
    this?.let {
        context.contentResolver.delete(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            MediaStore.Video.Media.DATA + "=?", arrayOf(it.absolutePath)
        )
    }
}

fun File?.getFileSize(): String {
    this?.let { return getFileSize(it.length().toDouble()) }
}

fun getFileSize(length: Double): String {
    val KiB = 1024
    val MiB = 1024 * 1024
    val decimalFormat = DecimalFormat("#.##")
    return when {
        length > MiB -> decimalFormat.format(length / MiB) + " MB"
        length > KiB -> decimalFormat.format(length / KiB) + " KB"
        else -> decimalFormat.format(length) + " B"
    }
}