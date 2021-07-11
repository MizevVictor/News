package com.victor.testapp.di

import com.victor.testapp.data.repository.NewsRepository
import com.victor.testapp.data.repository.NewsRepositoryImpl
import com.victor.testapp.data.api.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped


@Module
@InstallIn(ActivityComponent::class)
object NewsActivityModule {

    @Provides
    @ActivityScoped
    fun provideNewsApiService(): NewsApiService = NewsApiService()

    @Provides
    @ActivityScoped
    fun provideNewsRepo(
        apiService: NewsApiService,
    ): NewsRepository = NewsRepositoryImpl(apiService)

}
