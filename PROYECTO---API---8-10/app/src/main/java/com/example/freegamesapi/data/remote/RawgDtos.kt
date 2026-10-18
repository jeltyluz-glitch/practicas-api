package com.example.freegamesapi.data.remote

import com.google.gson.annotations.SerializedName

data class RawgResponseDto(
    @SerializedName("results") val results: List<RawgGameDto>?,
)

data class RawgGameDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("background_image") val backgroundImage: String?,
    @SerializedName("rating") val rating: Double?,
    @SerializedName("released") val released: String?,
)
