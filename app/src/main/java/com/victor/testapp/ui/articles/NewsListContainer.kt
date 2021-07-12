package com.victor.testapp.ui.articles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.victor.testapp.ui.ErrorView
import com.victor.testapp.ui.style.NewsTheme


@Composable
fun NewsListContainer(
    uiState: ArticleListUiState,
    retry: () -> Unit
) {
    Surface(
        color = NewsTheme.colors.backGroundColor,
        shape = RoundedCornerShape(topLeft = 5.dp, topRight = 5.dp),
        modifier = Modifier.fillMaxSize().padding(
            start = 10.dp,
            end = 10.dp,
            bottom = 50.dp
        )
    ) {
        when {
            uiState.isLoading -> {
                CircularLoader()
            }
            uiState.error != null -> {
                uiState.error.errorMessage?.let {
                    ErrorView(
                        errorMessage = it,
                        showRetry = uiState.error.showRetry,
                        retry = retry
                    )
                }
            }
            uiState.list?.isEmpty() == false -> {
                ArticleList(
                    articles = uiState.list
                )
            }
        }
    }
}


@Composable
private fun CircularLoader() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = NewsTheme.colors.circularLoaderColor
        )
    }
}

