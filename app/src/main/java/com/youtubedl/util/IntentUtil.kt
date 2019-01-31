package com.youtubedl.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.FileProvider
import android.widget.Toast
import com.youtubedl.OpenForTesting
import com.youtubedl.R
import java.io.File
import javax.inject.Inject

/**
 * Created by cuongpm on 1/13/19.
 */

@OpenForTesting
class IntentUtil @Inject constructor() {

    fun openFolder(context: Context?, path: String) {
        context?.let {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(path), "resource/*")

            if (intent.resolveActivityInfo(it.packageManager, 0) != null) {
                it.startActivity(intent)
            } else {
                Toast.makeText(it, it.getString(R.string.settings_message_open_folder), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun shareVideo(context: Context, file: File) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "video/*"
        val uri = FileProvider.getUriForFile(context, context.packageName + ".provider", file)
        intent.putExtra(Intent.EXTRA_STREAM, uri)

        if (intent.resolveActivityInfo(context.packageManager, 0) != null) {
            context.startActivity(Intent.createChooser(intent, "Share via:"))
        } else {
            Toast.makeText(context, context.getString(R.string.video_share_message), Toast.LENGTH_SHORT).show()
        }
    }
}