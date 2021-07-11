package com.victor.testapp.activity

import NewsAppUI
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import com.victor.testapp.ui.style.NewsTheme
import com.victor.testapp.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkTheme = viewModel.isDarkTheme.observeAsState(false)
            NewsTheme(darkTheme = darkTheme.value) {
                NewsAppUI(viewModel = viewModel)
            }
        }
    }
}
