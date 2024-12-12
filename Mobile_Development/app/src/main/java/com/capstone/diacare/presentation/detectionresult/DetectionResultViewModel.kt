package com.capstone.diacare.presentation.detectionresult

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.diacare.data.model.Prediction
import com.capstone.diacare.data.model.PredictionResult
import com.capstone.diacare.domain.datasource.PredictionDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetectionResultViewModel @Inject constructor(
    private val predictionDataSource: PredictionDataSource
) : ViewModel() {

    private var mResult: PredictionResult? = null
    private var mUri: Uri? = null

    private val _onSuccessAddHistory: MutableLiveData<Boolean> = MutableLiveData(false)
    val onSuccessAddHistory: LiveData<Boolean>
        get() = _onSuccessAddHistory

    fun initializeData(result: PredictionResult?, uri: Uri?) {
        mResult = result
        mUri = uri
    }

    fun getResult() = mResult
    fun getUri() = mUri

    fun insertHistory(date: String, uri: Uri, prediction: Prediction) {
        viewModelScope.launch {
            val defferedResult = async { predictionDataSource.insertHistory(date, uri, prediction) }
            _onSuccessAddHistory.value = defferedResult.await()

        }
    }
}