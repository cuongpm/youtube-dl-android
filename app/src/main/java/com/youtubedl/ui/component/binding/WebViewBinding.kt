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
    fun loadUrl(webView: WebView, url: String) {
        webView.loadUrl(url)
    }

    @BindingAdapter("app:javaScriptEnabled")
    @JvmStatic
    fun javaScriptEnabled(webView: WebView, isEnabled: String) {
        webView.settings.javaScriptEnabled = true
    }

    @BindingAdapter("app:addJavascriptInterface")
    @JvmStatic
    fun addJavascriptInterface(webView: WebView, name: String) {
        webView.addJavascriptInterface(webView.context, name)
    }

    @BindingAdapter("app:webViewClient")
    @JvmStatic
    fun webViewClient(webView: WebView, webViewClient: WebViewClient) {
        webView.webViewClient = webViewClient
    }

    @BindingAdapter("app:webChromeClient")
    @JvmStatic
    fun webChromeClient(webView: WebView, webChromeClient: WebChromeClient) {
        webView.webChromeClient = webChromeClient
    }
}