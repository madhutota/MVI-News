package com.newsmvi.data.repository

import com.newsmvi.data.model.ArticleDTO

interface NewsRepository {
    suspend fun getTopRatedBBCNews(sources: String):List<ArticleDTO>
}