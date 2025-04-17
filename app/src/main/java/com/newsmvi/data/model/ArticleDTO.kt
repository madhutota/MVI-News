package com.newsmvi.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDTO(
    @SerialName("description")
    val description: String,
    @SerialName("publishedAt")
    val title: String,
    @SerialName("urlToImage")
    val urlToImage: String
)