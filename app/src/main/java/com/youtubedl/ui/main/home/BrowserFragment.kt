package com.youtubedl.ui.main.home

import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import com.youtubedl.databinding.FragmentBrowserBinding
import com.youtubedl.di.ActivityScoped
import com.youtubedl.ui.component.adapter.SuggestionAdapter
import com.youtubedl.ui.component.adapter.TopPageAdapter
import com.youtubedl.ui.component.dialog.DownloadVideoListener
import com.youtubedl.ui.component.dialog.showDownloadVideoDialog
import com.youtubedl.ui.main.base.BaseFragment
import com.youtubedl.ui.main.player.VideoPlayerActivity
import com.youtubedl.ui.main.player.VideoPlayerFragment.Companion.VIDEO_NAME
import com.youtubedl.ui.main.player.VideoPlayerFragment.Companion.VIDEO_URL
import com.youtubedl.util.AppUtil.hideSoftKeyboard
import com.youtubedl.util.AppUtil.showSoftKeyboard
import javax.inject.Inject

/**
 * Created by cuongpm on 12/7/18.
 */

@ActivityScoped
class BrowserFragment @Inject constructor() : BaseFragment() {

    companion object {
        fun newInstance() = BrowserFragment()
    }

    private lateinit var browserViewModel: BrowserViewModel

    private lateinit var dataBinding: FragmentBrowserBinding

    private lateinit var topPageAdapter: TopPageAdapter

    private lateinit var suggestionAdapter: SuggestionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        browserViewModel = (activity as MainActivity).browserViewModel
        topPageAdapter = TopPageAdapter(ArrayList(0), browserViewModel)
        suggestionAdapter = SuggestionAdapter(context, ArrayList(0), browserViewModel)

        dataBinding = FragmentBrowserBinding.inflate(inflater, container, false).apply {
            this.viewModel = browserViewModel
            this.webChromeClient = browserWebChromeClient
            this.webViewClient = browserWebViewClient
            this.pageAdapter = topPageAdapter
            this.sgAdapter = suggestionAdapter
            this.onKeyListener = onKeyPressEnterListener
            this.textWatcher = onInputChangeListener
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        browserViewModel.start()
        handleUIEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        browserViewModel.stop()
    }

    private fun onBackPressed() {
        when {
            dataBinding.webview.canGoBack() -> dataBinding.webview.goBack()
            browserViewModel.isShowPage.get() -> browserViewModel.isShowPage.set(false)
            else -> activity?.finish()
        }
    }

    private fun handleUIEvents() {
        browserViewModel.apply {
            changeFocusEvent.observe(this@BrowserFragment, Observer { isFocus ->
                isFocus?.let { if (it) showSoftKeyboard(dataBinding.etSearch) else hideSoftKeyboard(dataBinding.etSearch) }
            })

            pressBackBtnEvent.observe(this@BrowserFragment, Observer { onBackPressed() })

            showDownloadDialogEvent.observe(this@BrowserFragment, Observer { videoInfo ->
                showDownloadVideoDialog(activity as MainActivity, object : DownloadVideoListener {
                    override fun onPreviewVideo(dialog: BottomSheetDialog) {
                        dialog.dismiss()
                        videoInfo?.let {
                            val intent = Intent(context, VideoPlayerActivity::class.java)
                            intent.putExtra(VIDEO_URL, it.downloadUrl)
                            intent.putExtra(VIDEO_NAME, it.name)
                            startActivity(intent)
                        }
                    }

                    override fun onDownloadVideo(dialog: BottomSheetDialog) {
                        browserViewModel.downloadVideoEvent.value = videoInfo
                        dialog.dismiss()
                    }

                    override fun onCancel(dialog: BottomSheetDialog) {
                        dialog.dismiss()
                    }
                })
            })
        }
    }

    private val onInputChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            val input = s.toString()
            browserViewModel.textInput.set(input)
            browserViewModel.showSuggestions()
            browserViewModel.publishSubject.onNext(input)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    private val onKeyPressEnterListener = View.OnKeyListener { v, keyCode, _ ->
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            browserViewModel.loadPage((v as EditText).text.toString())
            return@OnKeyListener true
        }
        return@OnKeyListener false
    }

    private val browserWebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            browserViewModel.progress.set(newProgress)
            super.onProgressChanged(view, newProgress)
        }
    }

    private val browserWebViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            browserViewModel.startPage(view.url)
            super.onPageStarted(view, url, favicon)
        }


        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            browserViewModel.textInput.set(url)
            browserViewModel.pageUrl.set(url)
            return super.shouldOverrideUrlLoading(view, url)
        }

        override fun onLoadResource(view: WebView, url: String) {
            browserViewModel.loadResource(view.url)
            super.onLoadResource(view, url)
        }

        override fun onPageFinished(view: WebView, url: String) {
            browserViewModel.finishPage(view.url)
            super.onPageFinished(view, url)
        }
    }
}