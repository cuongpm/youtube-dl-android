package com.youtubedl.ui.component.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youtubedl.R
import com.youtubedl.data.local.model.LocalVideo
import com.youtubedl.databinding.ItemVideoBinding

/**
 * Created by cuongpm on 12/7/18.
 */

class VideoAdapter(
    private var localVideos: List<LocalVideo>,
    private val videoListener: VideoListener
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = DataBindingUtil.inflate<ItemVideoBinding>(
            LayoutInflater.from(parent.context), R.layout.item_video, parent, false
        )

        return VideoViewHolder(binding)
    }

    override fun getItemCount() = localVideos.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) =
        holder.bind(localVideos[position], videoListener)

    class VideoViewHolder(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(localVideo: LocalVideo, videoListener: VideoListener) {
            with(binding)
            {
                this.localVideo = localVideo
                this.videoListener = videoListener
                executePendingBindings()
            }
        }
    }

    fun setData(localVideos: List<LocalVideo>) {
        this.localVideos = localVideos
        notifyDataSetChanged()
    }
}

interface VideoListener {
    fun onItemClicked(localVideo: LocalVideo)
    fun onMenuClicked(view: View, localVideo: LocalVideo)
}