package com.example.freegamesapi.data.remote

import com.google.gson.annotations.SerializedName

data class RickAndMortyResponseDto(
    @SerializedName("results") val results: List<RickAndMortyCharacterDto>?
)

data class RickAndMortyCharacterDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("species") val species: String?,
    @SerializedName("image") val image: String?
)
