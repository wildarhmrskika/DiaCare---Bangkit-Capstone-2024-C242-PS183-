package com.capstone.diacare.domain.datasource

import com.capstone.diacare.data.model.NewsResult
import com.capstone.diacare.data.remote.NetworkResult
import com.capstone.diacare.data.remote.handleApi
import com.capstone.diacare.data.remote.retrofit.NewsService
import com.capstone.diacare.di.NetworkModule
import javax.inject.Inject

class ArticleDataSource @Inject constructor(
    @NetworkModule.NewsUrl private val articleService: NewsService
) {
    suspend fun getAllNews(): NetworkResult<NewsResult> {
        return handleApi { articleService.getAllNews() }
    }
}