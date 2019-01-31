package com.youtubedl.ui.main.settings

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.ViewModelProvider
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.youtubedl.R
import com.youtubedl.util.FileUtil
import com.youtubedl.util.IntentUtil
import com.youtubedl.util.SingleLiveEvent
import com.youtubedl.util.SystemUtil
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.ViewModelUtil
import util.rule.InjectedFragmentTestRule
import java.io.File


/**
 * Created by cuongpm on 1/29/19.
 */

class SettingsFragmentTest {

    private lateinit var fileUtil: FileUtil

    private lateinit var systemUtil: SystemUtil

    private lateinit var intentUtil: IntentUtil

    private lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var settingsFragment: SettingsFragment

    private lateinit var settingsViewModel: SettingsViewModel

    private lateinit var clearCookiesEvent: SingleLiveEvent<Void>

    private lateinit var openVideoFolderEvent: SingleLiveEvent<Void>

    private lateinit var file: File

    private val screen = Screen()

    private val path = "videoPath"

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val uiRule = InjectedFragmentTestRule<SettingsFragment> {
        it.fileUtil = fileUtil
        it.systemUtil = systemUtil
        it.intentUtil = intentUtil
        it.viewModelFactory = viewModelFactory
    }

    @Before
    fun setup() {
        fileUtil = mock()
        systemUtil = mock()
        intentUtil = mock()
        file = mock()
        settingsViewModel = mock()
        clearCookiesEvent = SingleLiveEvent()
        openVideoFolderEvent = SingleLiveEvent()
        viewModelFactory = ViewModelUtil.createFor(settingsViewModel)
        doReturn(clearCookiesEvent).`when`(settingsViewModel).clearCookiesEvent
        doReturn(openVideoFolderEvent).`when`(settingsViewModel).openVideoFolderEvent
        doReturn(file).`when`(fileUtil).folderDir
        doReturn(path).`when`(file).path
    }

    @Test
    fun initial_ui() {
        screen.start()
        screen.hasSettings()
    }

    @Test
    fun clear_cookies() {
        screen.start()
        clearCookiesEvent.call()
        verify(systemUtil).clearCookies(uiRule.fragment.context)
    }

    @Test
    fun open_video_folder() {
        screen.start()
        openVideoFolderEvent.call()
        verify(intentUtil).openFolder(uiRule.fragment.context, path)
    }

    inner class Screen {
        fun start() {
            settingsFragment = SettingsFragment()
            uiRule.launchFragment(settingsFragment)
        }

        fun hasSettings() {
            onView(withId(R.id.tv_general)).check(matches(isDisplayed()))
            onView(withId(R.id.layout_folder)).check(matches(isDisplayed()))
            onView(withId(R.id.layout_clear_cookie)).check(matches(isDisplayed()))
        }
    }
}