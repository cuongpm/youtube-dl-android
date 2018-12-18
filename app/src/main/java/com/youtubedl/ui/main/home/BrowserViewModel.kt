package com.youtubedl.ui.main.home

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.youtubedl.data.local.room.entity.PageInfo
import com.youtubedl.data.repository.TopPagesRepository
import com.youtubedl.ui.main.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

class BrowserViewModel @Inject constructor(
    private val topPagesRepository: TopPagesRepository
) : BaseViewModel() {

    private var disposable: Disposable? = null

    val items: ObservableList<PageInfo> = ObservableArrayList()

    override fun start() {
        getTopPages()
    }

    override fun stop() {
        disposable?.let { it -> if (!it.isDisposed) it.dispose() }
    }

    private fun getTopPages() {
        disposable = topPagesRepository.getTopPages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                with(items) {
                    clear()
                    addAll(list)
                }
            }, { error ->
                error.printStackTrace()
            })
    }
}