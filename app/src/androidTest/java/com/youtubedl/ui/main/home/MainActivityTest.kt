package com.youtubedl.ui.main.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.databinding.ObservableField
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.youtubedl.R
import com.youtubedl.util.SingleLiveEvent
import com.youtubedl.util.fragment.FragmentFactory
import com.youtubedl.util.fragment.StubbedFragmentFactory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.ViewModelUtil
import util.rule.InjectedActivityTestRule

/**
 * Created by cuongpm on 2/6/19.
 */

class MainActivityTest {

    private lateinit var fragmentFactory: FragmentFactory

    private lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: MainViewModel

    private val pressBackBtnEvent = SingleLiveEvent<Void>()

    private val currentItem = ObservableField<Int>()

    private val screen = Screen()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val uiRule = InjectedActivityTestRule(MainActivity::class.java) {
        it.viewModelFactory = viewModelFactory
        it.fragmentFactory = fragmentFactory
    }

    @Before
    fun setup() {
        fragmentFactory = StubbedFragmentFactory()
        mainViewModel = mock()
        viewModelFactory = ViewModelUtil.createFor(mainViewModel)
        doReturn(pressBackBtnEvent).`when`(mainViewModel).pressBackBtnEvent
        doReturn(currentItem).`when`(mainViewModel).currentItem
    }

    @Test
    fun initial_ui() {
        screen.start()
        screen.hasBottomBar()
    }

    @Test
    fun test_press_back_button() {
        screen.start()
        screen.swipeLeftViewPager()

        assertEquals(1, currentItem.get())

        screen.pressBackButton()

        assertEquals(0, currentItem.get())

        screen.pressBackButton()

        assertNull(pressBackBtnEvent.value)
    }

    @Test
    fun test_press_bottom_bar() {
        screen.start()
        screen.openProgressTab()

        assertEquals(1, currentItem.get())

        screen.openVideoTab()

        assertEquals(2, currentItem.get())

        screen.openSettingsTab()

        assertEquals(3, currentItem.get())

        screen.openBrowserTab()

        assertEquals(0, currentItem.get())
    }

    inner class Screen {

        fun start() {
            uiRule.launchActivity(Intent())
        }

        fun pressBackButton() {
            uiRule.activity.onBackPressed()
        }

        fun hasBottomBar() {
            onView(withId(R.id.bottom_bar)).check(matches(isDisplayed()))
            onView(withText("Browser")).check(matches(isDisplayed()))
            onView(withText("Progress")).check(matches(isDisplayed()))
            onView(withText("Video")).check(matches(isDisplayed()))
            onView(withText("Settings")).check(matches(isDisplayed()))
        }

        fun swipeLeftViewPager() {
            onView(withId(R.id.view_pager)).perform(swipeLeft())
        }

        fun openBrowserTab() {
            onView(withText("Browser")).perform(click())
        }

        fun openProgressTab() {
            onView(withText("Progress")).perform(click())
        }

        fun openVideoTab() {
            onView(withText("Video")).perform(click())
        }

        fun openSettingsTab() {
            onView(withText("Settings")).perform(click())
        }
    }
}