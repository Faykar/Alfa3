package com.bagicode.alfa3.user.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.bagicode.alfa3.R
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_home, container, false)
//        val mWebView = webview.findViewById<WebView>(R.id.webview)
//        mWebView.loadUrl("google.com")
//
//        // Enable Javascript
//
//        // Enable Javascript
//        val webSettings: WebSettings = mWebView.getSettings()
//        webSettings.javaScriptEnabled = true
//
//        // Force links and redirects to open in the WebView instead of in a browser
//
//        // Force links and redirects to open in the WebView instead of in a browser
//        mWebView.setWebViewClient(WebViewClient())

        return v
    }
}