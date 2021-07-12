package com.victor.testapp.ui.reader

import com.victor.testapp.data.Result


data class ReaderUiState(

    val isLoading: Boolean = true,
    val error: Result.Error? = null //TODO: Implement error handling

)
