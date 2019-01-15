package com.youtubedl.ui.component.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.youtubedl.R
import com.youtubedl.data.local.room.entity.ProgressInfo
import com.youtubedl.databinding.ItemProgressBinding

/**
 * Created by cuongpm on 12/23/18.
 */

class ProgressAdapter(
    private var progressInfos: List<ProgressInfo>
) : RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val binding = DataBindingUtil.inflate<ItemProgressBinding>(
            LayoutInflater.from(parent.context), R.layout.item_progress, parent, false
        )

        return ProgressViewHolder(binding)
    }

    override fun getItemCount() = progressInfos.size

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) = holder.bind(progressInfos[position])

    class ProgressViewHolder(val binding: ItemProgressBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(progressInfo: ProgressInfo) {
            with(binding)
            {
                this.progressInfo = progressInfo
                executePendingBindings()
            }
        }
    }

    fun setData(progressInfos: List<ProgressInfo>) {
        this.progressInfos = progressInfos
        notifyDataSetChanged()
    }
}