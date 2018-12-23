package com.youtubedl.ui.component.binding

import android.databinding.BindingAdapter
import android.view.View
import android.widget.EditText

/**
 * Created by cuongpm on 12/22/18.
 */

object EditTextBinding {

    @BindingAdapter("app:onKeyListener")
    @JvmStatic
    fun setOnKeyListener(editText: EditText, onKeyListener: View.OnKeyListener) {
        editText.setOnKeyListener(onKeyListener)
    }
}