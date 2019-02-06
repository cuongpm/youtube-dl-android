package com.youtubedl.ui.main.progress

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
    lateinit var mainActivity: MainActivity

    private lateinit var progressViewModel: ProgressViewModel

    private lateinit var dataBinding: FragmentProgressBinding

    private lateinit var progressAdapter: ProgressAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        progressViewModel = mainActivity.progressViewModel
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        progressViewModel.stop()
    }

}