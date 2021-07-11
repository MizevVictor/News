package com.victor.testapp.ui

import com.victor.testapp.data.Result
import com.victor.testapp.data.response.Article


data class ArticleListUiState(
    val isLoading: Boolean = true,
    val list: List<Article>? = emptyList(),
    val error: Result.Error? = null
)
