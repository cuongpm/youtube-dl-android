package com.youtubedl.ui.component.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.youtubedl.R
import com.youtubedl.data.local.room.entity.PageInfo
import com.youtubedl.databinding.ItemTopPageBinding
import com.youtubedl.ui.main.home.BrowserViewModel

/**
 * Created by cuongpm on 12/16/18.
 */

class TopPageAdapter(
    private var pageInfos: List<PageInfo>,
    private val browserViewModel: BrowserViewModel?
) : RecyclerView.Adapter<TopPageAdapter.TopPageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopPageViewHolder {
        val binding = DataBindingUtil.inflate<ItemTopPageBinding>(
            LayoutInflater.from(parent.context), R.layout.item_top_page, parent, false
        )

        return TopPageViewHolder(binding)
    }

    override fun getItemCount() = pageInfos.size

    override fun onBindViewHolder(holder: TopPageViewHolder, position: Int) =
        holder.bind(pageInfos[position], object : TopPagesListener {
            override fun onItemClicked(pageInfo: PageInfo) {
                browserViewModel?.loadPage(pageInfo.link)
            }
        })

    class TopPageViewHolder(val binding: ItemTopPageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pageInfo: PageInfo, topPagesListener: TopPagesListener) {
            with(binding)
            {
                this.pageInfo = pageInfo
                this.listener = topPagesListener
                executePendingBindings()
            }
        }
    }

    fun setData(pageInfos: List<PageInfo>) {
        this.pageInfos = pageInfos
        notifyDataSetChanged()
    }

    interface TopPagesListener {
        fun onItemClicked(pageInfo: PageInfo)
    }
}
