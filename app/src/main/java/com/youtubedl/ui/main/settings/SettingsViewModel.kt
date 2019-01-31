package com.youtubedl.ui.main.settings

import com.youtubedl.OpenForTesting
import com.youtubedl.ui.main.base.BaseViewModel
import com.youtubedl.util.SingleLiveEvent
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

@OpenForTesting
class SettingsViewModel @Inject constructor() : BaseViewModel() {

    val clearCookiesEvent = SingleLiveEvent<Void>()
    val openVideoFolderEvent = SingleLiveEvent<Void>()

    override fun start() {
    }

    override fun stop() {
    }

    fun clearCookies() {
        clearCookiesEvent.call()
    }

    fun openVideoFolder() {
        openVideoFolderEvent.call()
    }
}
