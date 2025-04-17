package com.newsmvi.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("articles")
    val articleDTOS: List<ArticleDTO>
)