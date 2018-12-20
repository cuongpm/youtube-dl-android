package com.youtubedl.ui.main.home

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.youtubedl.databinding.FragmentBrowserBinding
import com.youtubedl.di.ActivityScoped
import com.youtubedl.ui.component.adapter.TopPageAdapter
import com.youtubedl.ui.main.base.BaseFragment
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

@ActivityScoped
class BrowserFragment @Inject constructor() : BaseFragment() {

    companion object {
        fun newInstance() = BrowserFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var browserViewModel: BrowserViewModel

    private lateinit var dataBinding: FragmentBrowserBinding

    private lateinit var topPageAdapter: TopPageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        browserViewModel = ViewModelProviders.of(this, viewModelFactory).get(BrowserViewModel::class.java)
        topPageAdapter = TopPageAdapter(ArrayList(0), browserViewModel)

        dataBinding = FragmentBrowserBinding.inflate(inflater, container, false).apply {
            this.viewModel = browserViewModel
            this.webChromeClient = browserWebChromeClient
            this.webViewClient = browserWebViewClient
            this.adapter = topPageAdapter
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        browserViewModel.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        browserViewModel.stop()
    }

    private val browserWebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            browserViewModel.progress.set(newProgress)
            super.onProgressChanged(view, newProgress)
        }
    }

    private val browserWebViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            browserViewModel.textInput.set(view.url)
            browserViewModel.isShowPage.set(true)
            browserViewModel.isShowProgress.set(true)
//            mBinding.fab.setVisibility(View.VISIBLE)
//            checkLinkStatus(view.url)
//            updateBookmarkMenu(view)
            super.onPageStarted(view, url, favicon)
        }


        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            browserViewModel.textInput.set(url)
            browserViewModel.pageUrl.set(url)
            return super.shouldOverrideUrlLoading(view, url)
        }

        override fun onLoadResource(view: WebView, url: String) {
            browserViewModel.textInput.set(view.url)
//            checkLinkStatus(view.url)
            if (url.contains("facebook.com")) {
//                view.loadUrl(ScriptUtil.FACEBOOK_SCRIPT)
            }
            super.onLoadResource(view, url)
        }

        override fun onPageFinished(view: WebView, url: String) {
            browserViewModel.textInput.set(view.url)
            browserViewModel.isShowProgress.set(false)
//            checkLinkStatus(view.url)
//            getPresenter().saveWebViewHistory(view)
//            updateBookmarkMenu(view)
            super.onPageFinished(view, url)
        }
    }
}