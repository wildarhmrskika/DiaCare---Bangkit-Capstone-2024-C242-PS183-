package com.capstone.diacare.presentation.home

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.diacare.data.model.ChatEntity
import com.capstone.diacare.data.model.PredictionEntity
import com.capstone.diacare.data.model.PredictionResult
import com.capstone.diacare.data.model.PredictionTestServerResult
import com.capstone.diacare.data.remote.NetworkResult
import com.capstone.diacare.domain.datasource.ChatDataSource
import com.capstone.diacare.domain.datasource.PredictionDataSource
import com.capstone.diacare.utils.MessageType
import com.capstone.diacare.utils.handleDiabetesQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val predictionDataSource: PredictionDataSource,
    private val chatDataSource: ChatDataSource
) : ViewModel() {

    private val _test: MutableLiveData<PredictionTestServerResult> = MutableLiveData()
    val test: LiveData<PredictionTestServerResult>
        get() = _test

    private val _predictionResult: MutableLiveData<PredictionResult> = MutableLiveData()
    val predictionResult: LiveData<PredictionResult>
        get() = _predictionResult


    private val _savedImageUri: MutableLiveData<Uri?> = MutableLiveData(null)
    val savedImageUri: LiveData<Uri?>
        get() = _savedImageUri

    private val _loadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private fun setLoading(show: Boolean) {
        _loadingState.value = show
    }

    private val _historyData: MutableLiveData<List<PredictionEntity>> = MutableLiveData()
    val historyData: LiveData<List<PredictionEntity>>
        get() = _historyData

    private val _onDeleteSuccess: MutableLiveData<Unit> = MutableLiveData()
    val onDeleteSuccess: LiveData<Unit>
        get() = _onDeleteSuccess

    private val _onGetChatData: MutableLiveData<List<ChatEntity>> = MutableLiveData(listOf())
    val onGetChatData: LiveData<List<ChatEntity>>
        get() = _onGetChatData

    init {
        testServer()
    }

    fun loadMessages() {
        viewModelScope.launch {
            _onGetChatData.postValue(chatDataSource.getAllMessage())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(message: String) {
        viewModelScope.launch {
            chatDataSource.insertMessage(
                ChatEntity(
                    message = message,
                    timeStamp = Instant.now().toString(),
                    messageType = MessageType.USER.name
                )
            )
            loadMessages()
            delay(3000L)
            val botResponse = handleDiabetesQuery(message)
            chatDataSource.insertMessage(
                ChatEntity(
                    message = botResponse,
                    timeStamp = Instant.now().toString(),
                    messageType = MessageType.BOT.name
                )
            )
            loadMessages()

        }
    }

    fun saveUri(uri: Uri? = null) {
        viewModelScope.launch {
            _savedImageUri.postValue(uri)
        }

    }

    private fun testServer() = viewModelScope.launch {
        setLoading(true)
        predictionDataSource.testServer().let {
            when (it) {
                is NetworkResult.Error -> {
                    setLoading(false)
                    _test.value = (PredictionTestServerResult(it.message, it.code.toString()))
                }

                is NetworkResult.Exception -> {
                    setLoading(false)
                    _test.value = PredictionTestServerResult(it.e.message, it.e.message)
                }

                is NetworkResult.Success -> {
                    setLoading(false)
                    _test.value = (it.data)
                }
            }
        }
    }

    fun analyze(data: MultipartBody.Part) = viewModelScope.launch {
        setLoading(true)
        Log.d("VM-analyze", "analyze: pre-predict")
        predictionDataSource.predictImage(data).let {
            when (it) {
                is NetworkResult.Error -> {
                    setLoading(false)
                }

                is NetworkResult.Exception -> {
                    setLoading(false)
                }

                is NetworkResult.Success -> {
                    setLoading(false)
                    _predictionResult.value = (it.data)
                }
            }
        }
    }

    fun getAllHistory() {
        viewModelScope.launch {
            setLoading(true)
            val defferedResult = async { predictionDataSource.getAllHistory() }
            _historyData.value = defferedResult.await()
            setLoading(false)
        }
    }

    fun deleteHistory(data: PredictionEntity) {
        viewModelScope.launch {
            setLoading(true)
            val deferredResult = async { predictionDataSource.deleteHistory(data) }
            deferredResult.await()
            _onDeleteSuccess.value = Unit
            setLoading(false)

        }
    }


}