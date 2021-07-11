package com.victor.testapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    public static final String EXTRA_URL = "EXTRA_URL";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);
        String url = getIntent().getStringExtra(EXTRA_URL);
        WebView webView = findViewById(R.id.web_view);
        webView.loadUrl(url);

        webView.getSettings().setJavaScriptEnabled(true);
    }
}
