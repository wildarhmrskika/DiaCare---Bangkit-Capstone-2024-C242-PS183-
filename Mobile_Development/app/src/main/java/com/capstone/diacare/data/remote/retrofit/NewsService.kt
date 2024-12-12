package com.capstone.diacare.data.remote.retrofit

import com.capstone.diacare.data.model.NewsResult
import retrofit2.Response
import retrofit2.http.GET

interface NewsService {

    @GET("/articles")
    suspend fun getAllNews() : Response<NewsResult>
}