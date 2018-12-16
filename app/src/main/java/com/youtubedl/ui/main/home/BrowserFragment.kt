package com.youtubedl.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youtubedl.databinding.FragmentBrowserBinding
import com.youtubedl.di.ActivityScoped
import com.youtubedl.ui.component.adapter.TopPageAdapter
import com.youtubedl.ui.main.base.BaseFragment
import java.util.ArrayList
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

@ActivityScoped
class BrowserFragment @Inject constructor() : BaseFragment() {

    companion object {
        fun newInstance() = BrowserFragment()
    }

    private lateinit var dataBinding: FragmentBrowserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = FragmentBrowserBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as MainActivity).browserViewModel
            adapter = TopPageAdapter(ArrayList(0))
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.viewModel?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataBinding.viewModel?.stop()
    }

}