package com.youtubedl.ui.main.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.databinding.ObservableField
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.youtubedl.R
import com.youtubedl.data.local.room.entity.VideoInfo
import com.youtubedl.util.SingleLiveEvent
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import util.ViewModelUtil
import util.rule.InjectedActivityTestRule

/**
 * Created by cuongpm on 2/6/19.
 */

class MainActivityTest {

    private lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: MainViewModel

    private val downloadVideoEvent = SingleLiveEvent<VideoInfo>()

    private val currentItem = ObservableField<Int>()

    private val screen = Screen()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val uiRule = InjectedActivityTestRule(MainActivity::class.java) {
        it.viewModelFactory = viewModelFactory
    }

    @Before
    fun setup() {
        mainViewModel = mock()
        viewModelFactory = ViewModelUtil.createFor(mainViewModel)
        doReturn(downloadVideoEvent).`when`(mainViewModel).downloadVideoEvent
        doReturn(currentItem).`when`(mainViewModel).currentItem
    }

    @Test
    @Ignore
    fun initial_ui() {
        screen.start()
        screen.hasBottomBar(true)
    }

    inner class Screen {

        fun start() {
            uiRule.launchActivity(Intent())
        }

        fun hasBottomBar(isShown: Boolean) {
            onView(withId(R.id.bottom_bar)).check(matches(if (isShown) isDisplayed() else not(isDisplayed())))
        }
    }
}