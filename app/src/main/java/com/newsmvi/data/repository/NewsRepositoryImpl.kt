package com.newsmvi.data.repository

import com.newsmvi.data.api.ApiService
import com.newsmvi.data.model.ArticleDTO

class NewsRepositoryImpl(private var apiService: ApiService) : NewsRepository {
    override suspend fun getTopRatedBBCNews(sources: String): List<ArticleDTO> {
        return apiService.getTopRatedBBCNews(sources).articleDTOS
    }


}