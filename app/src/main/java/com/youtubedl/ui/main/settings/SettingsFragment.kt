package com.youtubedl.ui.main.settings

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youtubedl.OpenForTesting
import com.youtubedl.databinding.FragmentSettingsBinding
import com.youtubedl.di.ActivityScoped
import com.youtubedl.ui.main.base.BaseFragment
import com.youtubedl.util.FileUtil
import com.youtubedl.util.IntentUtil
import com.youtubedl.util.SystemUtil
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

@OpenForTesting
@ActivityScoped
class SettingsFragment @Inject constructor() : BaseFragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    @Inject
    lateinit var fileUtil: FileUtil

    @Inject
    lateinit var intentUtil: IntentUtil

    @Inject
    lateinit var systemUtil: SystemUtil

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var dataBinding: FragmentSettingsBinding

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        settingsViewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)

        dataBinding = FragmentSettingsBinding.inflate(inflater, container, false).apply {
            this.viewModel = settingsViewModel
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsViewModel.start()
        handleUIEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        settingsViewModel.stop()
    }

    private fun handleUIEvents() {
        settingsViewModel.apply {
            clearCookiesEvent.observe(this@SettingsFragment, Observer {
                systemUtil.clearCookies(context)
            })

            openVideoFolderEvent.observe(this@SettingsFragment, Observer {
                intentUtil.openFolder(context, fileUtil.folderDir.path)
            })
        }
    }
}