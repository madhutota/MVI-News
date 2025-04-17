package com.newsmvi.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("articles")
    val articleDTOS: List<ArticleDTO>,
    @SerialName("status")
    val status: String,
    @SerialName("totalResults")
    val totalResults: Int
)