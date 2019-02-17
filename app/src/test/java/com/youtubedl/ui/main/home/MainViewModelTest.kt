package com.youtubedl.ui.main.home

import org.junit.Before
import org.junit.Test

/**
 * Created by cuongpm on 1/14/19.
 */

class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        mainViewModel = MainViewModel()
    }

    @Test
    fun `test MainViewModel here`() {
        mainViewModel.start()
        mainViewModel.stop()
    }
}