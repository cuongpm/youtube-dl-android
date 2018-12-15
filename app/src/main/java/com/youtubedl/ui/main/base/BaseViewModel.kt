package com.youtubedl.ui.main.base

import android.arch.lifecycle.ViewModel

/**
 * Created by cuongpm on 12/15/18.
 */

abstract class BaseViewModel : ViewModel() {

    abstract fun start()

    abstract fun stop()
}