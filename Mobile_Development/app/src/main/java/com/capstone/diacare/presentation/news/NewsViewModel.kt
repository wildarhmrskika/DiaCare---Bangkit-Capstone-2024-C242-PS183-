package com.capstone.diacare.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.diacare.data.model.NewsResult
import com.capstone.diacare.data.remote.NetworkResult
import com.capstone.diacare.domain.datasource.ArticleDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val articleDataSource: ArticleDataSource
) : ViewModel() {

    private val _newsResult: MutableLiveData<NewsResult> = MutableLiveData()
    val newsResult: LiveData<NewsResult>
        get() = _newsResult

    private val _loadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    init {
        loadAllNews()
    }

    private fun loadAllNews() {
        setLoading(true)
        viewModelScope.launch {
            articleDataSource.getAllNews().let {
                when (it) {
                    is NetworkResult.Error -> {
                        setLoading(false)
                    }

                    is NetworkResult.Exception -> {
                        setLoading(false)
                    }

                    is NetworkResult.Success -> {
                        setLoading(false)
                        _newsResult.value = (it.data)
                    }
                }
            }
        }

    }
    private fun setLoading(show: Boolean) {
        _loadingState.value = show
    }
}