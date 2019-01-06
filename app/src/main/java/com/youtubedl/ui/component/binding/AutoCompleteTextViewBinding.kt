package com.youtubedl.ui.component.binding

import android.databinding.BindingAdapter
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.youtubedl.data.local.model.Suggestion
import com.youtubedl.ui.component.adapter.SuggestionAdapter

/**
 * Created by cuongpm on 12/23/18.
 */

object AutoCompleteTextViewBinding {

    @BindingAdapter("app:adapter")
    @JvmStatic
    fun AutoCompleteTextView.setAdapter(adapter: ArrayAdapter<Suggestion>) {
        setAdapter(adapter)
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun AutoCompleteTextView.setSuggestions(items: List<Suggestion>) {
        with(adapter as SuggestionAdapter?) {
            this?.setData(items)
        }
    }
}