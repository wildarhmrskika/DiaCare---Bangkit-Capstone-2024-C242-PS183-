package com.capstone.diacare.data.remote.retrofit

import com.capstone.diacare.data.model.PredictionResult
import com.capstone.diacare.data.model.PredictionTestServerResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PredictionService {
    @Multipart
    @POST("/predict")
    suspend fun predictImage(
        @Part file : MultipartBody.Part
    ): Response<PredictionResult>

    @GET("/health")
    suspend fun testServer() : Response<PredictionTestServerResult>

}