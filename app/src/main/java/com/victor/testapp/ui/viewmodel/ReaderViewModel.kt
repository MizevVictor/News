package com.victor.testapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.testapp.ui.reader.ReaderUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element


/**
 * Created by Viktor Mizev on 12.07.2021.
 */
class ReaderViewModel(val url: String) : ViewModel() {

    private val _uiState = MutableLiveData<ReaderUiState>().apply {
        value = ReaderUiState()
    }
    val uiState: LiveData<ReaderUiState> = _uiState

    private val _html = MutableLiveData<String>().apply {
        value = ""
    }
    val html: LiveData<String> = _html

    init {
        getAndCleanHtml()
    }

    private fun getAndCleanHtml() {
        viewModelScope.launch {
            _uiState.value = uiState.value?.copy(isLoading = true)

            val document =
                withContext(Dispatchers.IO) {
                    Jsoup.connect(url)
                        .get()
                }

            val cleanedArticle = withContext(Dispatchers.Default) {
                val article = document.getElementsByClass("post post-page__article").first()

                removeElementsFromHtml(
                    article,
                    "post-meta post__block post__block_post-meta",
                    "post-actions post__block post__block_post-actions",
                    "article-subscription-widget",
                    "tags-list post__block post__block_tags",
                    "related-list"
                )
                article
            }

            _html.value = cleanedArticle.toString()
            _uiState.value = uiState.value?.copy(isLoading = false)
        }

    }

    private fun removeElementsFromHtml(element: Element?, vararg elementNames: String) =
        elementNames.forEach { element?.getElementsByClass(it)?.first()?.remove() }

    fun performAction(action: Action) {
        when (action) {
            Action.PageLoadStarted -> _uiState.value = uiState.value?.copy(isLoading = true)
            Action.PageLoaded -> _uiState.value = uiState.value?.copy(isLoading = false)

        }
    }

    sealed class Action {
        object PageLoadStarted : Action()
        object PageLoaded : Action()
    }
}