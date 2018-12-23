package com.youtubedl.ui.main.home

import android.databinding.*
import android.util.Patterns
import com.youtubedl.data.local.model.Suggestion
import com.youtubedl.data.local.room.entity.PageInfo
import com.youtubedl.data.repository.ConfigRepository
import com.youtubedl.data.repository.TopPagesRepository
import com.youtubedl.ui.main.base.BaseViewModel
import com.youtubedl.util.SingleLiveEvent
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

class BrowserViewModel @Inject constructor(
    private val topPagesRepository: TopPagesRepository,
    private val configRepository: ConfigRepository
) : BaseViewModel() {

    companion object {
        private const val SEARCH_URL = "https://www.google.com/search?q=%s"
    }

    private lateinit var compositeDisposable: CompositeDisposable

    lateinit var publishSubject: PublishSubject<String>

    val textInput = ObservableField<String>("")
    val isFocus = ObservableBoolean(false)
    val changeFocusEvent = SingleLiveEvent<Boolean>()

    val pageUrl = ObservableField<String>("")
    val isShowPage = ObservableBoolean(false)
    val javaScriptEnabled = ObservableBoolean(true)
    val javaScripInterface = ObservableField<String>("browser")

    val isShowProgress = ObservableBoolean(false)
    val progress = ObservableInt(0)

    val isShowFabBtn = ObservableBoolean(false)

    val listPages: ObservableList<PageInfo> = ObservableArrayList()

    val listSuggestions: ObservableList<Suggestion> = ObservableArrayList()

    val pressBackBtnEvent = SingleLiveEvent<Void>()

    override fun start() {
        compositeDisposable = CompositeDisposable()
        publishSubject = PublishSubject.create()
        getTopPages()
    }

    override fun stop() {
        compositeDisposable.clear()
    }

    fun loadPage(input: String) {
        if (input.isNotEmpty()) {
            isShowPage.set(true)
            changeFocus(false)

            if (input.startsWith("http://") || input.startsWith("https://")) {
                pageUrl.set(input)
            } else if (Patterns.WEB_URL.matcher(input).matches()) {
                pageUrl.set("http://$input")
                textInput.set("http://$input")
            } else {
                pageUrl.set(String.format(SEARCH_URL, input))
                textInput.set(String.format(SEARCH_URL, input))
            }
        }
    }

    fun changeFocus(isFocus: Boolean) {
        this.isFocus.set(isFocus)
        changeFocusEvent.value = isFocus
    }

    fun startPage(url: String) {
        textInput.set(url)
        isShowPage.set(true)
        isShowProgress.set(true)
        isShowFabBtn.set(true)
    }

    fun finishPage(url: String) {
        textInput.set(url)
        isShowProgress.set(false)
    }

    fun showSuggestions() {
        getListSuggestions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                with(listSuggestions) {
                    clear()
                    addAll(list)
                }
            }.let { compositeDisposable.add(it) }
    }

    private fun getListSuggestions(): Flowable<List<Suggestion>> {
        return Flowable.combineLatest(
            publishSubject.debounce(300, TimeUnit.MILLISECONDS).toFlowable(BackpressureStrategy.LATEST),
            configRepository.getSupportedPages(),
            BiFunction { input, supportedPages ->
                val listSuggestions = mutableListOf<Suggestion>()
                supportedPages.filter { page -> page.name.contains(input) }.map {
                    listSuggestions.add(Suggestion(content = it.name))
                }
                listSuggestions
            }
        )
    }

    private fun getTopPages() {
        topPagesRepository.getTopPages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                with(listPages) {
                    clear()
                    addAll(list)
                }
            }, { error ->
                error.printStackTrace()
            }).let { compositeDisposable.add(it) }
    }
}