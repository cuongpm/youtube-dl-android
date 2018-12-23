package com.youtubedl.ui.component.binding

import android.databinding.BindingAdapter
import android.widget.*
import com.youtubedl.data.local.model.Suggestion
import com.youtubedl.ui.component.adapter.SuggestionAdapter

/**
 * Created by cuongpm on 12/23/18.
 */

object AutoCompleteTextViewBinding {

    @BindingAdapter("app:adapter")
    @JvmStatic
    fun setAdapter(autoCompleteTextView: AutoCompleteTextView, adapter: ArrayAdapter<Suggestion>) {
        autoCompleteTextView.setAdapter(adapter)
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun setSuggestions(autoCompleteTextView: AutoCompleteTextView, items: List<Suggestion>) {
        with(autoCompleteTextView.adapter as SuggestionAdapter?) {
            this?.setData(items)
        }
    }
}