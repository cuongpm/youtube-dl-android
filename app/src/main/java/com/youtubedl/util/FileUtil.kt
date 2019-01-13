package com.youtubedl.util

import android.os.Environment
import java.io.File
import javax.inject.Inject

/**
 * Created by cuongpm on 1/9/19.
 */

class FileUtil @Inject constructor() {

    companion object {
        const val FOLDER_NAME = "YoutubeDL"
    }

    val folderDir: File
        get() = File(Environment.getExternalStorageDirectory(), FOLDER_NAME)

    val listFiles: List<File>
        get() {
            val files = folderDir.listFiles()
            return files?.let { files.toList() } ?: run { arrayListOf<File>() }
        }
}
