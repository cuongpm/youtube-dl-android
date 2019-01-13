package com.youtubedl.util.ext

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.youtubedl.R
import java.io.File
import java.text.DecimalFormat

/**
 * Created by cuongpm on 1/9/19.
 */

private fun File?.scanMedia(context: Context) {
    this?.let {
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        intent.data = Uri.fromFile(it)
        context.sendBroadcast(intent)
    }
}

private fun File?.deleteMedia(context: Context) {
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

fun File?.renameFile(context: Context, onFileChangedCallback: OnFileChangedCallback) {
    this?.let {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

            val etName = EditText(context).apply {
                layoutParams =
                        ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                setText(nameWithoutExtension)
                setSelection(text.length)
                setTextColor(Color.BLACK)
                imeOptions = EditorInfo.IME_ACTION_DONE
                inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                setSingleLine()
            }

            val layout = LinearLayout(context).apply {
                layoutParams =
                        ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                orientation = LinearLayout.VERTICAL
                setPadding(80, 40, 80, 20)
                addView(etName)
            }

            AlertDialog.Builder(context).setTitle(context.getString(R.string.rename_title)).setView(layout)
                .setNegativeButton(context.getString(android.R.string.cancel)) { _, _ ->
                    imm.hideSoftInputFromWindow(etName.windowToken, 0)
                }
                .setPositiveButton(context.getString(android.R.string.ok)) { _, _ ->
                    val fileName = etName.text.toString().trim()
                    if (fileName.isNotEmpty()) {
                        val newFile = File(this.parent, fileName + extension)
                        if (newFile.exists()) {
                            Toast.makeText(context, context.getString(R.string.rename_exist), Toast.LENGTH_SHORT).show()
                        } else if (this.renameTo(newFile)) {
                            this.deleteMedia(context)
                            newFile.scanMedia(context)
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

interface OnFileChangedCallback {
    fun renameFileCompleted(fileName: String)
}
