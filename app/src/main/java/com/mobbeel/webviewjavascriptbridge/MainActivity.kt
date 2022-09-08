package com.mobbeel.webviewjavascriptbridge

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private val JS_INTERFACE = "store"

    private lateinit var button: Button
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        button.setOnClickListener {
            sendData()
        }
        webView = findViewById(R.id.webview)
        setupWebView()
    }

    /** Setup the WebView and load the URL  */
    private fun sendData() {
        val map: LinkedHashMap<String, Int> = LinkedHashMap()
        map["prop1"] = 1234
        map["prop2"] = 5678
        val json = JSONObject(map as Map<*, *>)
        webView.loadUrl("javascript:showData($json)")
    }

    /** Setup the WebView and load the URL  */
    private fun setupWebView() {
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebAppInterface(this), JS_INTERFACE)
        webView.loadUrl("file:///android_asset/index.html")
    }

    /** Instantiate the interface and set the context  */
    class WebAppInterface(private val mContext: Context) {
        /** Show a toast from the web page  */
        @JavascriptInterface
        fun showToast(message: String) {
            val toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

}