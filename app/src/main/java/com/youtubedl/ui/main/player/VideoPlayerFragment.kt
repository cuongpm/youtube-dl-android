package com.youtubedl.ui.main.player

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youtubedl.databinding.FragmentPlayerBinding
import com.youtubedl.di.ActivityScoped
import com.youtubedl.ui.main.base.BaseFragment
import javax.inject.Inject

/**
 * Created by cuongpm on 1/6/19.
 */

@ActivityScoped
class VideoPlayerFragment @Inject constructor() : BaseFragment() {

    companion object {
        const val VIDEO_URL = "video_url"
        const val VIDEO_NAME = "video_name"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var videoPlayerViewModel: VideoPlayerViewModel

    private lateinit var dataBinding: FragmentPlayerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        videoPlayerViewModel = ViewModelProviders.of(this, viewModelFactory).get(VideoPlayerViewModel::class.java)

        dataBinding = FragmentPlayerBinding.inflate(inflater, container, false).apply {
            this.viewModel = videoPlayerViewModel
            this.navigationListener = navigationIconClickListener
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(VIDEO_URL)?.let { videoPlayerViewModel.videoUrl.set(it) }
        arguments?.getString(VIDEO_NAME)?.let { videoPlayerViewModel.videoName.set(it) }

        videoPlayerViewModel.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        videoPlayerViewModel.stop()
    }

    private val navigationIconClickListener = View.OnClickListener { activity?.finish() }
}