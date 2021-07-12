package com.victor.testapp.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.victor.testapp.R
import com.victor.testapp.ui.style.NewsTheme
import titleStyle

/**
 * Created by Viktor Mizev on 12.07.2021.
 */
@Composable
fun CircularLoader() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = NewsTheme.colors.circularLoaderColor
        )
    }
}

@Composable
fun ErrorView(
    errorMessage: String,
    showRetry: Boolean,
    retry: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage,
            style = titleStyle.copy(color = NewsTheme.colors.titleColor)
        )
        if (showRetry) {
            TextButton(onClick = retry) {
                Text(
                    text = stringResource(id = R.string.retry),
                    style = TextStyle(
                        color = NewsTheme.colors.sourceColor
                    )
                )
            }
        }
    }
}