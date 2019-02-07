package com.youtubedl.ui.main.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.youtubedl.R
import com.youtubedl.data.local.model.Suggestion
import com.youtubedl.data.local.room.entity.PageInfo
import com.youtubedl.data.local.room.entity.VideoInfo
import com.youtubedl.util.AppUtil
import com.youtubedl.util.SingleLiveEvent
import io.reactivex.subjects.PublishSubject
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.RecyclerViewMatcher.Companion.recyclerViewWithId
import util.RecyclerViewUtil.recyclerViewSizeIs
import util.rule.InjectedFragmentTestRule
import util.waitUntil
import java.util.concurrent.Callable

/**
 * Created by cuongpm on 1/29/19.
 */
class BrowserFragmentTest {

    private lateinit var mainActivity: MainActivity

    private lateinit var appUtil: AppUtil

    private lateinit var browserFragment: BrowserFragment

    private lateinit var browserViewModel: BrowserViewModel

    private lateinit var publishSubject: PublishSubject<String>

    private lateinit var pageInfo1: PageInfo

    private lateinit var pageInfo2: PageInfo

    private val listPages = ObservableArrayList<PageInfo>()

    private val listSuggestions = ObservableArrayList<Suggestion>()

    private val changeFocusEvent = SingleLiveEvent<Boolean>()

    private val pressBackBtnEvent = SingleLiveEvent<Void>()

    private val showDownloadDialogEvent = SingleLiveEvent<VideoInfo>()

    private val downloadVideoEvent = SingleLiveEvent<VideoInfo>()

    private val pageUrl = ObservableField<String>("")

    private val textInput = ObservableField<String>("")

    private val isShowPage = ObservableBoolean(false)

    private val isFocus = ObservableBoolean(false)

    private val isShowProgress = ObservableBoolean(false)

    private val progress = ObservableInt(0)

    private val screen = Screen()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val uiRule = InjectedFragmentTestRule<BrowserFragment> {
        it.mainActivity = mainActivity
        it.appUtil = appUtil
    }

    @Before
    fun setup() {
        mainActivity = mock()
        appUtil = mock()
        browserViewModel = mock()
        publishSubject = PublishSubject.create()
        doReturn(browserViewModel).`when`(mainActivity).browserViewModel
        doReturn(publishSubject).`when`(browserViewModel).publishSubject
        doReturn(changeFocusEvent).`when`(browserViewModel).changeFocusEvent
        doReturn(pressBackBtnEvent).`when`(browserViewModel).pressBackBtnEvent
        doReturn(showDownloadDialogEvent).`when`(browserViewModel).showDownloadDialogEvent
        doReturn(downloadVideoEvent).`when`(browserViewModel).downloadVideoEvent

        pageInfo1 = PageInfo(name = "Facebook", link = "https://facebook.com")
        pageInfo2 = PageInfo(name = "Youtube", link = "https://youtube.com")
        listPages.addAll(listOf(pageInfo1, pageInfo2))
        doReturn(listPages).`when`(browserViewModel).listPages
        doReturn(listSuggestions).`when`(browserViewModel).listSuggestions
        doReturn(pageUrl).`when`(browserViewModel).pageUrl
        doReturn(textInput).`when`(browserViewModel).textInput
        doReturn(isShowPage).`when`(browserViewModel).isShowPage
        doReturn(isFocus).`when`(browserViewModel).isFocus
        doReturn(isShowProgress).`when`(browserViewModel).isShowProgress
        doReturn(progress).`when`(browserViewModel).progress
    }

    @Test
    fun show_list_top_pages() {
        screen.start()

        waitUntil("Wait for rendering UI", Callable {
            verify(browserViewModel).start()
            screen.verifyListSize(2)
            screen.verifyListPages()
            true
        }, 500)
    }

    @Test
    fun open_a_page_in_top_pages() {
        screen.start()
        screen.openTopPage(0)
        verify(browserViewModel).loadPage(pageInfo1.link)

        isShowPage.set(true)
        isFocus.set(false)
        changeFocusEvent.value = false
        pageUrl.set(pageInfo1.link)

        waitUntil("Wait for rendering UI", Callable {
            screen.hasTopPages(false)
            screen.hasWebView(true)
            verify(appUtil).hideSoftKeyboard(any())
            true
        }, 500)
    }

    inner class Screen {
        fun start() {
            browserFragment = BrowserFragment()
            uiRule.launchFragment(browserFragment)
        }

        fun verifyListSize(size: Int) {
            onView(withId(R.id.rv_top_pages)).check(matches(recyclerViewSizeIs(size)))
        }

        fun verifyListPages() {
            onView(recyclerViewWithId(R.id.rv_top_pages).atPositionOnView(0, R.id.tv_name))
                .check(matches(withText(pageInfo1.name)))
            onView(recyclerViewWithId(R.id.rv_top_pages).atPositionOnView(1, R.id.tv_name))
                .check(matches(withText(pageInfo2.name)))
        }

        fun openTopPage(position: Int) {
            onView(recyclerViewWithId(R.id.rv_top_pages).atPosition(position)).perform(click())
        }

        fun hasTopPages(isShown: Boolean) {
            onView(withId(R.id.rv_top_pages)).check(matches(if (isShown) isDisplayed() else not(isDisplayed())))
        }

        fun hasWebView(isShown: Boolean) {
            onView(withId(R.id.webview)).check(matches(if (isShown) isDisplayed() else not(isDisplayed())))
        }
    }
}