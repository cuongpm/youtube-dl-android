package com.youtubedl.ui.component.binding

import android.databinding.BindingAdapter
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

/**
 * Created by cuongpm on 12/22/18.
 */

object EditTextBinding {

    @BindingAdapter("app:onKeyListener")
    @JvmStatic
    fun EditText.setOnKeyListener(onKeyListener: View.OnKeyListener) {
        setOnKeyListener(onKeyListener)
    }

    @BindingAdapter("app:textChangedListener")
    @JvmStatic
    fun EditText.addTextChangedListener(textWatcher: TextWatcher) {
        addTextChangedListener(textWatcher)
    }
}