package com.youtubedl.ui.main.splash

import android.app.Activity
import android.app.Instrumentation
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.nhaarman.mockito_kotlin.mock
import com.youtubedl.R
import com.youtubedl.ui.main.home.MainActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.ViewModelUtil
import util.rule.InjectedActivityTestRule

/**
 * Created by cuongpm on 2/11/19.
 */

class SplashActivityTest {

    private lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var splashViewModel: SplashViewModel

    private val screen = Screen()

    @get:Rule
    val uiRule = InjectedActivityTestRule(SplashActivity::class.java) {
        it.viewModelFactory = viewModelFactory
    }

    @Before
    fun setup() {
        splashViewModel = mock()
        viewModelFactory = ViewModelUtil.createFor(splashViewModel)
    }

    @Test
    fun initial_ui() {
        screen.start()
        screen.hasContent(true)

//        waitUntil("Wait for opening MainActivity", Callable {
//            screen.verifyOpenMainActivity()
//            true
//        }, 3000)
    }

    inner class Screen {

        fun start() {
            uiRule.launchActivity(Intent())
        }

        fun hasContent(isShown: Boolean) {
            onView(withId(R.id.img_icon)).check(matches(if (isShown) isDisplayed() else not(isDisplayed())))
            onView(withId(R.id.tv_name)).check(matches(if (isShown) isDisplayed() else not(isDisplayed())))
            onView(withId(R.id.progress_bar)).check(matches(if (isShown) isDisplayed() else not(isDisplayed())))
        }

        fun verifyOpenMainActivity() {
            intending(hasComponent(MainActivity::class.java.name))
                .respondWith(Instrumentation.ActivityResult(Activity.RESULT_CANCELED, null))
            intended(hasComponent(MainActivity::class.java.name))
            assertTrue(uiRule.activity.isFinishing)
        }
    }
}