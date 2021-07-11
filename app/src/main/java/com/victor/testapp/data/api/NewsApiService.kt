package com.victor.testapp.data.api

import android.annotation.SuppressLint
import com.victor.testapp.data.response.NewsResponse
import com.victor.testapp.BaseApplication
import java.io.File
import java.util.concurrent.TimeUnit
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {
    @GET("{category}")
    suspend fun getArticlesByCateGoryAsync(
        @Path("category") category: String,
    ): Response<NewsResponse>

    companion object {

        private const val HEADER_CACHE_CONTROL = "Cache-Control"
        private const val HEADER_PRAGMA = "Pragma"
        private const val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB

        operator fun invoke(): NewsApiService {
            val requestInterceptor = Interceptor { chain ->

                val response = chain.proceed(chain.request())

                val cacheControl = CacheControl.Builder()
                    .maxAge(5, TimeUnit.SECONDS)
                    .build()

                return@Interceptor response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build()
            }

            val cache = Cache(
                File(BaseApplication.instance.cacheDir, "networkCache"),
                cacheSize
            )

            val offlineInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url
                    .newBuilder()
                    .build()

                var request = chain.request()

                if (BaseApplication.isNetworkConnected()) {

                    request = request
                        .newBuilder()
                        .url(url)
                        .build()
                } else {

                    val cacheControl = CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build()

                    request = request
                        .newBuilder()
                        .url(url)
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build()
                }

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(offlineInterceptor)
                .addNetworkInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://cointelegraph.com/rss/tag/")
                .client(okHttpClient)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                .create(NewsApiService::class.java)
        }
    }
}
