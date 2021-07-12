package com.victor.testapp.data.repository
import com.victor.testapp.data.Result
import com.victor.testapp.data.api.NewsApiService
import com.victor.testapp.data.response.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
) : NewsRepository {
    private val defaultDispatcher = Dispatchers.Default
    override suspend fun getArticlesByCategoryAsync(
        category: String,
        page: Int
    ): Result<NewsResponse> {
        try {
            val response = newsApiService.getArticlesByCateGoryAsync(category)
            return if (response.isSuccessful) {
                if (response.body() != null) {
                    Result.Success(response.body()!!)
                } else {
                    Result.Error("No Data found")
                }
            } else {

                withContext(defaultDispatcher) {
                    Result.Error(
                        response.errorBody()?.string(),
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            var errorMessage = e.localizedMessage
            if (e.localizedMessage!!.contains("Unable to resolve host")) {
                errorMessage = "No internet connection"
            }
            return Result.Error(errorMessage ?: "Something went wrong")
        }
    }

}
