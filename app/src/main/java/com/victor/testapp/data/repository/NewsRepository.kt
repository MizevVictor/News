package com.victor.testapp.data.repository
import com.victor.testapp.data.Result
import com.victor.testapp.data.response.NewsResponse

interface NewsRepository {
    suspend fun getArticlesByCategoryAsync(category: String, page: Int): Result<NewsResponse>
}
