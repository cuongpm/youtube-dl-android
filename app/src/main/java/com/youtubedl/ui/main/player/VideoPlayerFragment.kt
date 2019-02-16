package com.youtubedl.ui.main.player

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youtubedl.OpenForTesting
import com.youtubedl.databinding.FragmentPlayerBinding
import com.youtubedl.ui.main.base.BaseFragment
import com.youtubedl.util.TimeUtil
import javax.inject.Inject

/**
 * Created by cuongpm on 1/6/19.
 */

@OpenForTesting
class VideoPlayerFragment : BaseFragment() {

    companion object {
        const val VIDEO_URL = "video_url"
        const val VIDEO_NAME = "video_name"
        private const val SEEK_INTERVAL = 5000
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var videoPlayerViewModel: VideoPlayerViewModel

    private lateinit var dataBinding: FragmentPlayerBinding

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        videoPlayerViewModel = ViewModelProviders.of(this, viewModelFactory).get(VideoPlayerViewModel::class.java)

        dataBinding = FragmentPlayerBinding.inflate(inflater, container, false).apply {
            this.viewModel = videoPlayerViewModel
            this.navigationListener = navigationIconClickListener
            this.onPreparedListener = onPreparedVideoListener
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

    private val onPreparedVideoListener = MediaPlayer.OnPreparedListener { player ->
        mediaPlayer = player
        val volume = videoPlayerViewModel.getVolume()
        mediaPlayer?.setVolume(volume, volume)

        val totalTime = TimeUtil.convertMilliSecondsToTimer(dataBinding.videoView.duration.toLong())
        videoPlayerViewModel.currentTime.set("00:00")
        videoPlayerViewModel.totalTime.set(totalTime)
    }
}