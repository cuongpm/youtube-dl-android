package com.youtubedl.ui.main.home

import android.databinding.ObservableField
import com.youtubedl.ui.main.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by cuongpm on 12/9/18.
 */

class MainViewModel @Inject constructor() : BaseViewModel() {

    val currentItem = ObservableField<Int>()

    override fun start() {
    }

    override fun stop() {
    }
}