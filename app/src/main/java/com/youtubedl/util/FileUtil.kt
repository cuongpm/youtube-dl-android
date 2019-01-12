package com.youtubedl.util

import android.os.Environment
import java.io.File
import java.text.DecimalFormat

/**
 * Created by cuongpm on 1/9/19.
 */

object FileUtil {

    private const val MiB = 1024 * 1024
    private const val KiB = 1024
    private val format = DecimalFormat("#.##")

    const val FOLDER_NAME = "YoutubeDL"

    val folderDir: File
        get() = File(Environment.getExternalStorageDirectory(), FOLDER_NAME)

    val listFiles: List<File>
        get() {
            val files = folderDir.listFiles()
            return files?.let { files.toList() } ?: run { arrayListOf<File>() }
        }

    fun getFileSize(length: Double): String {
        return when {
            length > MiB -> format.format(length / MiB) + " MB"
            length > KiB -> format.format(length / KiB) + " KB"
            else -> format.format(length) + " B"
        }
    }
}
