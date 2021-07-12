package com.victor.testapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victor.testapp.ui.articles.ReaderUi
import com.victor.testapp.ui.style.NewsTheme
import com.victor.testapp.ui.viewmodel.ReaderViewModel

/**
 * Created by Viktor Mizev on 12.07.2021.
 */
const val EXTRA_URL = "EXTRA_URL"

class ReaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra(EXTRA_URL)
        val viewModel = ViewModelProvider(
            this,
            ViewModelFactory(url)
        ).get(ReaderViewModel::class.java)
        setContent {
            NewsTheme(true) {
                ReaderUi(viewModel)
            }
        }
    }

    class ViewModelFactory(val url: String) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(url::class.java).newInstance(url);
        }

    }

}