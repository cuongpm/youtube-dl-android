package com.youtubedl.ui.component.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.youtubedl.data.local.model.Suggestion
import com.youtubedl.databinding.ItemSuggestionBinding
import com.youtubedl.ui.main.home.BrowserViewModel

/**
 * Created by cuongpm on 12/23/18.
 */

class SuggestionAdapter(
    context: Context?,
    private var suggestions: List<Suggestion>,
    private val browserViewModel: BrowserViewModel?
) : ArrayAdapter<Suggestion>(context, 0) {

    override fun getCount() = suggestions.size

    override fun getItem(position: Int) = suggestions[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        val binding = if (view == null) {
            val inflater = LayoutInflater.from(viewGroup.context)
            ItemSuggestionBinding.inflate(inflater, viewGroup, false)
        } else {
            DataBindingUtil.getBinding(view)!!
        }

        val suggestionListener = object : SuggestionListener {
            override fun onItemClicked(suggestion: Suggestion) {
                browserViewModel?.loadPage(suggestion.content)
            }
        }

        with(binding) {
            this.suggestion = suggestions[position]
            this.listener = suggestionListener
            executePendingBindings()
        }

        return binding.root
    }

    fun setData(suggestions: List<Suggestion>) {
        this.suggestions = suggestions
        notifyDataSetChanged()
    }

    interface SuggestionListener {
        fun onItemClicked(suggestion: Suggestion)
    }
}
