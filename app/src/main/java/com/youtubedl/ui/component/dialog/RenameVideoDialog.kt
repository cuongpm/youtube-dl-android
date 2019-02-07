package com.youtubedl.ui.component.dialog

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import com.youtubedl.R
import com.youtubedl.util.AppUtil

/**
 * Created by cuongpm on 1/20/19.
 */

fun showRenameVideoDialog(
    context: Context,
    appUtil: AppUtil,
    currentName: String,
    onClickListener: View.OnClickListener
) {
    val etName = EditText(context).apply {
        layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setText(currentName)
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

    appUtil.showSoftKeyboard(etName)

    AlertDialog.Builder(context)
        .setTitle(context.getString(R.string.video_rename_title))
        .setView(layout)
        .setNegativeButton(context.getString(android.R.string.cancel)) { _, _ ->
            appUtil.hideSoftKeyboard(etName)
        }
        .setPositiveButton(context.getString(android.R.string.ok)) { _, _ ->
            appUtil.hideSoftKeyboard(etName)
            onClickListener.onClick(etName)
        }.show()
}