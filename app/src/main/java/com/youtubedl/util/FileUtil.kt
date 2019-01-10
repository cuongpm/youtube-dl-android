package com.youtubedl.util

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.text.TextUtils
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.youtubedl.R
import java.io.File
import java.text.DecimalFormat

/**
 * Created by cuongpm on 1/9/19.
 */

object FileUtil {

    const val FOLDER_NAME = "YoutubeDL"

    private const val MiB = 1024 * 1024
    private const val KiB = 1024

    private val format = DecimalFormat("#.##")

    val folderDir: File
        get() = File(Environment.getExternalStorageDirectory(), FOLDER_NAME)

//    val listFiles: ArrayList<File>
//        get() {
//            val files = folderDir.listFiles()
//            return if (files == null) ArrayList<Any>() else ArrayList(Arrays.asList(*files))
//        }

    fun getFileSize(file: File): String {
        try {
            val length = file.length().toDouble()
            return getFileSize(length)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

    }

    fun getFileSize(length: Double): String {
        if (length > MiB) {
            return format.format(length / MiB) + " MB"
        }
        return if (length > KiB) {
            format.format(length / KiB) + " KB"
        } else format.format(length) + " B"
    }

    fun getFileExtension(path: String): String {
        try {
            val uri = Uri.parse(path)
            return MimeTypeMap.getFileExtensionFromUrl(uri.toString().replace(" ", ""))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun scanMedia(context: Context, file: File) {
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        intent.data = Uri.fromFile(file)
        context.sendBroadcast(intent)
    }

    private fun deleteMedia(context: Context, file: File) {
        context.contentResolver.delete(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            MediaStore.Video.Media.DATA + "=?", arrayOf(file.absolutePath)
        )
    }

    fun renameFile(context: Context, file: File, onFileChangedCallback: OnFileChangedCallback) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

            val extension = "." + getFileExtension(file.path)
            val currentName = file.name.substring(0, file.name.length - extension.length)

            val layout = LinearLayout(context)
            layout.layoutParams =
                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layout.orientation = LinearLayout.VERTICAL
            layout.setPadding(80, 40, 80, 20)
            val etName = EditText(context)
            etName.layoutParams =
                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            etName.setText(currentName)
            etName.setSelection(etName.text.length)
            etName.setTextColor(Color.BLACK)
            etName.imeOptions = EditorInfo.IME_ACTION_DONE
            etName.inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
            etName.setSingleLine()
            layout.addView(etName)

            AlertDialog.Builder(context).setTitle(context.getString(R.string.rename_title)).setView(layout)
                .setNegativeButton(context.getString(android.R.string.cancel)) { _, _ ->
                    imm.hideSoftInputFromWindow(etName.windowToken, 0)
                }
                .setPositiveButton(context.getString(android.R.string.ok)) { _, _ ->
                    val fileName = etName.text.toString().trim { it <= ' ' }
                    if (!TextUtils.isEmpty(fileName)) {
                        val newFile = File(folderDir, fileName + extension)
                        if (newFile.exists()) {
                            Toast.makeText(context, context.getString(R.string.rename_exist), Toast.LENGTH_SHORT).show()
                        } else if (file.renameTo(newFile)) {
                            deleteMedia(context, file)
                            scanMedia(context, newFile)
                            onFileChangedCallback.renameFileCompleted(newFile.name)
                        }
                    } else {
                        Toast.makeText(context, context.getString(R.string.rename_invalid), Toast.LENGTH_SHORT).show()
                    }
                    imm.hideSoftInputFromWindow(etName.windowToken, 0)
                }.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    interface OnFileChangedCallback {
        fun renameFileCompleted(fileName: String)
    }
}
