package com.youtubedl.ui.main.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youtubedl.databinding.FragmentVideoBinding
import com.youtubedl.di.ActivityScoped
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

    private lateinit var dataBinding: FragmentVideoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = FragmentVideoBinding.inflate(inflater, container, false).apply {
            //            viewModel = (activity as TaskActivity).taskViewModel
        }

        return dataBinding.root
    }

}