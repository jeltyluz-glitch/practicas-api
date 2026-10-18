package com.example.freegamesapi.data.remote

import com.google.gson.annotations.SerializedName

data class PokeResponseDto(
    @SerializedName("results") val results: List<PokeResultDto>?
)

data class PokeResultDto(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
)
