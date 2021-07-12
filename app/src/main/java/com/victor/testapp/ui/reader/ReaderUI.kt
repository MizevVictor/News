package com.victor.testapp.ui.articles

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Web
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.victor.testapp.R
import com.victor.testapp.ui.style.NewsTheme
import com.victor.testapp.ui.viewmodel.ReaderViewModel
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import org.sufficientlysecure.htmltextview.HtmlTextView


@Composable
fun ReaderUi(viewModel: ReaderViewModel) {
    val page = remember { mutableStateOf(0) }
    val webUrl = remember { mutableStateOf(viewModel.url) }
    val uiState = viewModel.uiState.observeAsState().value!!

    Scaffold(modifier = Modifier.padding(top = 25.dp), bodyContent = {

        val html = viewModel.html.observeAsState().value!!

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
                CircularProgressIndicator()
            }
        }
        when (page.value) {
            0 -> AndroidView(
                { context ->
                    HtmlTextView(context).apply {
                        setOnClickATagListener { _, _, href ->
                            href?.let {
                                webUrl.value = it
                                page.value = 1
                            }
                            true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
                    .then(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)),
                update = {
                    it.setHtml(html, HtmlHttpImageGetter(it))
                })
            1 -> AndroidView(
                { context ->
                    WebView(context).apply {
                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView, url: String) {
                                viewModel.performAction(ReaderViewModel.Action.PageLoaded)
                            }
                        }
                        viewModel.performAction(ReaderViewModel.Action.PageLoadStarted)
                        loadUrl(webUrl.value)

                    }
                },
                modifier = Modifier.fillMaxWidth()
                    .then(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)),
            )
        }
    }, bottomBar = {
        BottomAppBar(
            backgroundColor = NewsTheme.colors.bottomNavBackground,
            content = {
                BottomNavigation {
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                asset = vectorResource(R.drawable.ic_cointelegraph_logo),
                                modifier = Modifier.size(25.dp)
                            )
                        },
                        label = { Text(text = ContextAmbient.current.getString(R.string.bottom_bar_reader)) },
                        selected = page.value == 0,
                        onClick = {
                            page.value = 0
                        },
                        selectedContentColor = NewsTheme.colors.bottomNavActiveIconColor,
                        unselectedContentColor = NewsTheme.colors.bottomNavInActiveIconColor
                    )

                    BottomNavigationItem(
                        icon = {
                            Icon(
                                Icons.Filled.Web,
                                modifier = Modifier.size(25.dp)
                            )
                        },
                        label = { Text(text = ContextAmbient.current.getString(R.string.bottom_bar_webview)) },
                        selected = page.value == 1,
                        onClick = {
                            page.value = 1
                        },
                        selectedContentColor = NewsTheme.colors.bottomNavActiveIconColor,
                        unselectedContentColor = NewsTheme.colors.bottomNavInActiveIconColor
                    )


                }
            }
        )


    })
}







