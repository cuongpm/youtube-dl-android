package com.youtubedl.ui.main.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youtubedl.databinding.FragmentProgressBinding
import com.youtubedl.di.ActivityScoped
import com.youtubedl.ui.component.adapter.ProgressAdapter
import com.youtubedl.ui.main.base.BaseFragment
import com.youtubedl.ui.main.home.MainActivity
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

@ActivityScoped
class ProgressFragment @Inject constructor() : BaseFragment() {

    companion object {
        fun newInstance() = ProgressFragment()
    }

    private lateinit var progressViewModel: ProgressViewModel

    private lateinit var dataBinding: FragmentProgressBinding

    private lateinit var progressAdapter: ProgressAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        progressViewModel = (activity as MainActivity).progressViewModel
        progressAdapter = ProgressAdapter(ArrayList(0))

        dataBinding = FragmentProgressBinding.inflate(inflater, container, false).apply {
            this.viewModel = progressViewModel
            this.adapter = progressAdapter
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressViewModel.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        progressViewModel.stop()
    }

}