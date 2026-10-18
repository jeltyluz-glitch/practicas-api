package com.example.freegamesapi.data.remote

import com.google.gson.annotations.SerializedName

data class SpaceflightNewsResponseDto(
    @SerializedName("results") val results: List<ArticleDto>?
)

data class ArticleDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("summary") val summary: String?,
    @SerializedName("news_site") val newsSite: String?,
    @SerializedName("image_url") val imageUrl: String?
)
