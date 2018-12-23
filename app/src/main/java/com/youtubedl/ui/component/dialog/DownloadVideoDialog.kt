package com.youtubedl.ui.component.dialog

import android.app.Activity
import android.support.design.widget.BottomSheetDialog
import com.youtubedl.databinding.DialogDownloadVideoBinding

/**
 * Created by cuongpm on 12/23/18.
 */

fun showDownloadVideoDialog(activity: Activity, downloadVideoListener: DownloadVideoListener) {
    val bottomSheetDialog = BottomSheetDialog(activity)
    val binding = DialogDownloadVideoBinding.inflate(activity.layoutInflater, null, false).apply {
        this.listener = downloadVideoListener
        this.dialog = bottomSheetDialog
    }
    bottomSheetDialog.setContentView(binding.root)
    bottomSheetDialog.show()
}

interface DownloadVideoListener {
    fun onPreviewVideo(dialog: BottomSheetDialog)
    fun onDownloadVideo(dialog: BottomSheetDialog)
    fun onCancel(dialog: BottomSheetDialog)
}