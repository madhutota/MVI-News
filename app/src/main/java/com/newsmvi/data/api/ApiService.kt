package com.newsmvi.data.api

import com.newsmvi.data.local.AppConstants
import com.newsmvi.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getTopRatedBBCNews(
        @Query("sources") sources: String = AppConstants.BBC_SOURCES,
    ): NewsResponse

}