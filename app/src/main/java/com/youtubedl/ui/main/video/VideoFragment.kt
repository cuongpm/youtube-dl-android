package com.youtubedl.ui.main.video

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.youtubedl.R
import com.youtubedl.data.local.model.LocalVideo
import com.youtubedl.databinding.FragmentVideoBinding
import com.youtubedl.di.ActivityScoped
import com.youtubedl.ui.component.adapter.VideoAdapter
import com.youtubedl.ui.component.adapter.VideoListener
import com.youtubedl.ui.component.dialog.showRenameVideoDialog
import com.youtubedl.ui.main.base.BaseFragment
import com.youtubedl.ui.main.player.VideoPlayerActivity
import com.youtubedl.ui.main.player.VideoPlayerFragment
import com.youtubedl.ui.main.video.VideoViewModel.Companion.FILE_EXIST_ERROR_CODE
import com.youtubedl.util.IntentUtil.shareVideo
import java.io.File
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
        videoAdapter = VideoAdapter(ArrayList(0), videoListener)

        dataBinding = FragmentVideoBinding.inflate(inflater, container, false).apply {
            this.viewModel = videoViewModel
            this.rvVideo.layoutManager = LinearLayoutManager(context)
            this.rvVideo.adapter = videoAdapter
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoViewModel.start()
        handleUIEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        videoViewModel.stop()
    }

    private fun handleUIEvents() {
        videoViewModel.apply {
            renameErrorEvent.observe(this@VideoFragment, Observer { errorCode ->
                val errorMessage =
                    if (errorCode == FILE_EXIST_ERROR_CODE) R.string.video_rename_exist else R.string.video_rename_invalid
                Toast.makeText(context, context?.getString(errorMessage), Toast.LENGTH_SHORT).show()

            })
        }
    }

    private val videoListener = object : VideoListener {
        override fun onItemClicked(localVideo: LocalVideo) {
            val intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putExtra(VideoPlayerFragment.VIDEO_URL, localVideo.file.path)
            intent.putExtra(VideoPlayerFragment.VIDEO_NAME, localVideo.file.name)
            context?.startActivity(intent)
        }

        override fun onMenuClicked(view: View, localVideo: LocalVideo) {
            showPopupMenu(view, localVideo.file)
        }
    }

    private fun showPopupMenu(view: View, file: File) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menuInflater.inflate(R.menu.menu_video, popupMenu.menu)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener { arg0 ->
            when (arg0.itemId) {
                R.id.item_rename -> {
                    showRenameVideoDialog(view.context, file.nameWithoutExtension,
                        View.OnClickListener { v ->
                            with(v as EditText) {
                                videoViewModel.renameVideo(v.context, file, v.text.toString().trim())
                            }
                        })
                    true
                }
                R.id.item_delete -> {
                    videoViewModel.deleteVideo(file)
                    true
                }
                R.id.item_share -> {
                    shareVideo(view.context, file)
                    true
                }
                else -> false
            }
        }
    }
}