package com.example.freegamesapi.data.remote

import com.google.gson.annotations.SerializedName

data class CloudPersonajeDto(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("estado") val estado: String,
    @SerializedName("especie") val especie: String,
    @SerializedName("imagen") val imagen: String,
    @SerializedName("is_favorite") val isFavorite: Int
)

data class FavoritoUpdateDto(
    @SerializedName("is_favorite") val isFavorite: Boolean
)
