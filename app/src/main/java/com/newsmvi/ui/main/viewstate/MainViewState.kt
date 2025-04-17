package com.newsmvi.ui.main.viewstate

import com.newsmvi.data.model.ArticleDTO

sealed class MainViewState {

    object Idle : MainViewState()
    object Loading : MainViewState()
    data class Error(var message: String) : MainViewState()
    data class Success(var data: List<ArticleDTO>) : MainViewState()
}
