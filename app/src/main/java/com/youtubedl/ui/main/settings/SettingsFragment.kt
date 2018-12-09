package com.youtubedl.ui.main.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youtubedl.databinding.FragmentSettingsBinding
import com.youtubedl.di.ActivityScoped
import com.youtubedl.ui.main.base.BaseFragment
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

@ActivityScoped
class SettingsFragment @Inject constructor() : BaseFragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var dataBinding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = FragmentSettingsBinding.inflate(inflater, container, false).apply {
            //            viewModel = (activity as TaskActivity).taskViewModel
        }

        return dataBinding.root
    }

}