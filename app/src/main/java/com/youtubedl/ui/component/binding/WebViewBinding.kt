package com.youtubedl.ui.component.binding

import android.databinding.BindingAdapter
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * Created by cuongpm on 12/19/18.
 */

object WebViewBinding {

    @BindingAdapter("app:loadUrl")
    @JvmStatic
    fun WebView.loadUrl(url: String) {
        loadUrl(url)
    }

    @BindingAdapter("app:javaScriptEnabled")
    @JvmStatic
    fun WebView.javaScriptEnabled(isEnabled: Boolean) {
        settings.javaScriptEnabled = isEnabled
    }

    @BindingAdapter("app:addJavascriptInterface")
    @JvmStatic
    fun WebView.addJavascriptInterface(name: String) {
        addJavascriptInterface(context, name)
    }

    @BindingAdapter("app:webViewClient")
    @JvmStatic
    fun WebView.webViewClient(webViewClient: WebViewClient) {
        this.webViewClient = webViewClient
    }

    @BindingAdapter("app:webChromeClient")
    @JvmStatic
    fun WebView.webChromeClient(webChromeClient: WebChromeClient) {
        this.webChromeClient = webChromeClient
    }
}