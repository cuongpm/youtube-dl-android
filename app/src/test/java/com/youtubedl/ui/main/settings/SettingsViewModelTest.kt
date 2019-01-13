package com.youtubedl.ui.main.settings

import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by cuongpm on 1/14/19.
 */

class SettingsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setup() {
        viewModel = SettingsViewModel()
    }

    @Test
    fun `test clear cookies`() {
        viewModel.clearCookies()
        assertNull(viewModel.clearCookiesEvent.value)
    }

    @Test
    fun `test open video folder`() {
        viewModel.openVideoFolder()
        assertNull(viewModel.openVideoFolderEvent.value)
    }
}