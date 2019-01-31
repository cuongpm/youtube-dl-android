package com.youtubedl.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.text.DecimalFormat
import javax.inject.Inject

/**
 * Created by cuongpm on 1/9/19.
 */

@AllOpen
class FileUtil @Inject constructor() {

    companion object {
        const val FOLDER_NAME = "YoutubeDL"

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
    }

    val folderDir: File
        get() = File(Environment.getExternalStorageDirectory(), FOLDER_NAME)

    val listFiles: List<File>
        get() {
            val files = folderDir.listFiles()
            return files?.let { files.toList() } ?: run { arrayListOf<File>() }
        }

    fun scanMedia(context: Context, file: File) {
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        intent.data = Uri.fromFile(file)
        context.sendBroadcast(intent)
    }

    fun deleteMedia(context: Context, file: File) {
        context.contentResolver.delete(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            MediaStore.Video.Media.DATA + "=?", arrayOf(file.absolutePath)
        )
    }
}
