package com.newsmvi.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsmvi.data.local.AppConstants
import com.newsmvi.data.repository.NewsRepository
import com.newsmvi.ui.main.intent.MainIntent
import com.newsmvi.ui.main.viewstate.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var newsRepository: NewsRepository) : ViewModel() {
    val mainIntent: Channel<MainIntent> = Channel(Channel.UNLIMITED)
    private val _newsState = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val newsState: StateFlow<MainViewState>
        get() = _newsState
    init {
        getIntent()
    }
    private fun getIntent() {
        viewModelScope.launch {
            mainIntent.consumeAsFlow().collectLatest {
                when (it) {
                    is MainIntent.getTopRatedNews -> {
                        getTopRatedNew()
                    }
                }

            }
        }

    }
    private suspend fun getTopRatedNew() {
        _newsState.value = MainViewState.Loading
        try {
            _newsState.value = MainViewState.Success(
                newsRepository.getTopRatedBBCNews(AppConstants.BBC_SOURCES)
            )
        } catch (e: Exception) {
            _newsState.value = MainViewState.Error(e.localizedMessage ?: "Unknown error")
        }
    }

}