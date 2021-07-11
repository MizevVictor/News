package com.victor.testapp.ui.viewmodel

import com.victor.testapp.ui.ArticleListUiState
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.testapp.data.Result
import com.victor.testapp.data.repository.NewsRepository
import com.victor.testapp.data.response.NewsResponse
import com.victor.testapp.other.Altcoin
import com.victor.testapp.other.Category
import com.victor.testapp.other.Bitcoin
import com.victor.testapp.other.Blockchain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel @ViewModelInject constructor(private val repo: NewsRepository) : ViewModel() {

    private val _isDarkTheme = MutableLiveData<Boolean>().apply { value = false }
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    private val _categoryList = MutableLiveData<List<Category>>().apply {
        value = listOf(Bitcoin(), Altcoin(), Blockchain())
    }
    val categoryList: LiveData<List<Category>> = _categoryList

    private val _activeCategory = MutableLiveData<Category>().apply {
        value = categoryList.value!![0]
    }
    val activeCategory: LiveData<Category> = _activeCategory

    private var firstPageUiState = ArticleListUiState()
    private val _firstCategoryUiState = MutableLiveData<ArticleListUiState>().apply {
        value = ArticleListUiState()
    }
    val firstCategoryUiState: LiveData<ArticleListUiState> = _firstCategoryUiState
    private var secondPageUiState = ArticleListUiState()
    private val _secondCategoryUiState = MutableLiveData<ArticleListUiState>().apply {
        value = ArticleListUiState()
    }
    val secondCategoryUiState: LiveData<ArticleListUiState> = _secondCategoryUiState
    private var thirdPageUiState = ArticleListUiState()
    private val _thirdCategoryUiState = MutableLiveData<ArticleListUiState>().apply {
        value = ArticleListUiState()
    }
    val thirdCategoryUiState: LiveData<ArticleListUiState> = _thirdCategoryUiState

    init {
        getArticlesByCategory(categoryList.value!![0])
    }

    private fun getArticlesByCategory(
        category: Category,
        page: Int = 1
    ) {
        viewModelScope.launch {
            setLoadingState(category)
            when (val result = repo.getArticlesByCategoryAsync(category.category, page)) {
                is Result.Error -> {
                    withContext(Dispatchers.Main) {
                        setErrorState(category, Result.Error(result.errorMessage, result.showRetry))
                    }
                }
                is Result.Success -> {
                    withContext(Dispatchers.Main) {
                        setSuccessState(category, result.data)
                    }
                }
            }
        }
    }

    fun performAction(action: Action) {
        when (action) {
            is Action.ChangePageTo -> {
                _activeCategory.value = action.category
                when (categoryList.value?.indexOf(action.category)) {
                    0 -> {
                        _firstCategoryUiState.value = firstPageUiState
                        if (_firstCategoryUiState.value?.list == null || _firstCategoryUiState.value?.list?.isNullOrEmpty() == true) {
                            getArticlesByCategory(action.category)
                        }
                    }
                    1 -> {
                        _secondCategoryUiState.value = secondPageUiState
                        if (_secondCategoryUiState.value?.list == null || _secondCategoryUiState.value?.list?.isNullOrEmpty() == true) {
                            getArticlesByCategory(action.category)
                        }
                    }
                    2 -> {
                        _thirdCategoryUiState.value = thirdPageUiState
                        if (_thirdCategoryUiState.value?.list == null || _thirdCategoryUiState.value?.list?.isNullOrEmpty() == true) {
                            getArticlesByCategory(action.category)
                        }
                    }
                }
            }
            is Action.FetchArticles -> {
                getArticlesByCategory(action.category)
            }

        }
    }

    private fun setErrorState(category: Category, error: Result.Error) {
        when (categoryList.value?.indexOf(category)) {
            0 -> {
                firstPageUiState = firstPageUiState.copy(
                    isLoading = false,
                    list = null,
                    error = error
                )
                _firstCategoryUiState.value = firstPageUiState
            }
            1 -> {
                secondPageUiState = secondPageUiState.copy(
                    isLoading = false,
                    list = null,
                    error = error
                )
                _secondCategoryUiState.value = secondPageUiState
            }
            2 -> {
                thirdPageUiState = thirdPageUiState.copy(
                    isLoading = false,
                    list = null,
                    error = error
                )
                _thirdCategoryUiState.value = thirdPageUiState
            }
        }
    }

    private fun setSuccessState(category: Category, data: NewsResponse) {
        when (categoryList.value?.indexOf(category)) {
            0 -> {
                firstPageUiState = firstPageUiState.copy(
                    isLoading = false,
                    list = data.channel.items,
                    error = null
                )
                _firstCategoryUiState.value = firstPageUiState
            }
            1 -> {
                secondPageUiState = secondPageUiState.copy(
                    isLoading = false,
                    list = data.channel.items,
                    error = null
                )
                _secondCategoryUiState.value = secondPageUiState
            }
            2 -> {
                thirdPageUiState = thirdPageUiState.copy(
                    isLoading = false,
                    list = data.channel.items,
                    error = null
                )
                _thirdCategoryUiState.value = thirdPageUiState
            }
        }
    }

    private fun setLoadingState(category: Category) {
        when (categoryList.value?.indexOf(category)) {
            0 -> {
                _firstCategoryUiState.value = firstPageUiState.copy(isLoading = true)
            }
            1 -> {
                _secondCategoryUiState.value = secondPageUiState.copy(isLoading = true)
            }
            2 -> {
                _thirdCategoryUiState.value = thirdPageUiState.copy(isLoading = true)
            }
        }
    }

    sealed class Action {
        data class ChangePageTo(val category: Category) : Action()
        data class FetchArticles(val category: Category) : Action()
        object SwitchTheme : Action()
    }
}
