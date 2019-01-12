package com.youtubedl.ui.main.video

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youtubedl.databinding.FragmentVideoBinding
import com.youtubedl.di.ActivityScoped
import com.youtubedl.ui.component.adapter.VideoAdapter
import com.youtubedl.ui.main.base.BaseFragment
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

@ActivityScoped
class VideoFragment @Inject constructor() : BaseFragment() {

    companion object {
        fun newInstance() = VideoFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var dataBinding: FragmentVideoBinding

    private lateinit var videoViewModel: VideoViewModel

    private lateinit var videoAdapter: VideoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        videoViewModel = ViewModelProviders.of(this, viewModelFactory).get(VideoViewModel::class.java)
        videoAdapter = VideoAdapter(ArrayList(0))

        dataBinding = FragmentVideoBinding.inflate(inflater, container, false).apply {
            this.viewModel = videoViewModel
            this.adapter = videoAdapter
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoViewModel.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        videoViewModel.stop()
    }
}