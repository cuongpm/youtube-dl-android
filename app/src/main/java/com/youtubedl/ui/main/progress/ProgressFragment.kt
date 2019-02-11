package com.youtubedl.ui.main.progress

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youtubedl.OpenForTesting
import com.youtubedl.databinding.FragmentProgressBinding
import com.youtubedl.di.ActivityScoped
import com.youtubedl.ui.component.adapter.ProgressAdapter
import com.youtubedl.ui.main.base.BaseFragment
import com.youtubedl.ui.main.home.MainActivity
import com.youtubedl.ui.main.home.MainViewModel
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

@OpenForTesting
@ActivityScoped
class ProgressFragment @Inject constructor() : BaseFragment() {

    companion object {
        fun newInstance() = ProgressFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mainActivity: MainActivity

    private lateinit var progressViewModel: ProgressViewModel

    private lateinit var mainViewModel: MainViewModel

    private lateinit var dataBinding: FragmentProgressBinding

    private lateinit var progressAdapter: ProgressAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainViewModel = mainActivity.mainViewModel
        progressViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProgressViewModel::class.java)
        progressAdapter = ProgressAdapter(ArrayList(0))

        dataBinding = FragmentProgressBinding.inflate(inflater, container, false).apply {
            this.viewModel = progressViewModel
            this.rvProgress.layoutManager = LinearLayoutManager(context)
            this.rvProgress.adapter = progressAdapter
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressViewModel.start()
        handleDownloadVideoEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        progressViewModel.stop()
    }

    private fun handleDownloadVideoEvent() {
        mainViewModel.downloadVideoEvent.observe(this, Observer { videoInfo ->
            progressViewModel.downloadVideo(videoInfo)
        })
    }
}