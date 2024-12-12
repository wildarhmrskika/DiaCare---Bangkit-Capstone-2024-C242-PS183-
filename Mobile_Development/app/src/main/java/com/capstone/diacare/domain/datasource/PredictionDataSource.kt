package com.capstone.diacare.domain.datasource

import android.net.Uri
import android.util.Log
import com.capstone.diacare.data.local.PredictionDao
import com.capstone.diacare.data.model.Prediction
import com.capstone.diacare.data.model.PredictionEntity
import com.capstone.diacare.data.model.PredictionResult
import com.capstone.diacare.data.model.PredictionTestServerResult
import com.capstone.diacare.data.remote.NetworkResult
import com.capstone.diacare.data.remote.handleApi
import com.capstone.diacare.data.remote.retrofit.PredictionService
import com.capstone.diacare.di.NetworkModule
import okhttp3.MultipartBody
import javax.inject.Inject

class PredictionDataSource @Inject constructor(
    @NetworkModule.PredictUrl private val predictionService: PredictionService,
    private val predictionDao: PredictionDao
) {

    suspend fun predictImage(file: MultipartBody.Part): NetworkResult<PredictionResult> =
        handleApi {
            Log.d("DS-analyze", "analyze: pre-predict")
            predictionService.predictImage(file)
        }


    suspend fun testServer(): NetworkResult<PredictionTestServerResult> =
        handleApi { predictionService.testServer() }

    suspend fun insertHistory(date: String, uri: Uri, prediction: Prediction): Boolean {
        return try {
            predictionDao.insertPrediction(
                PredictionEntity(
                    result = prediction.jsonMemberClass ?: "",
                    confidence = prediction.confidence ?: 0.0,
                    imageUri = uri.toString(),
                    date = date
                )
            )
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getAllHistory(): List<PredictionEntity> {
        return try {
            predictionDao.getAllHistory()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun deleteHistory(data: PredictionEntity): Boolean {
        return try {
            predictionDao.deleteItem(data)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
