package com.youtubedl.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.youtubedl.OpenForTesting
import javax.inject.Inject

/**
 * Created by cuongpm on 12/22/18.
 */

@OpenForTesting
class AppUtil @Inject constructor() {

    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideSoftKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
}