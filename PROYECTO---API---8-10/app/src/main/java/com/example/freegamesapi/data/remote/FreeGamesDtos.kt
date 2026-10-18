package com.example.freegamesapi.data.remote

import com.google.gson.annotations.SerializedName

/**
 * DTO que refleja los campos que devuelve la API de FreeToGame (https://www.freetogame.com/api/).
 */
data class GameDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("short_description") val shortDescription: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("game_url") val gameUrl: String?,
    @SerializedName("genre") val genre: String?,
    @SerializedName("platform") val platform: String?,
    @SerializedName("publisher") val publisher: String?,
    @SerializedName("developer") val developer: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("freetogame_profile_url") val freetogameProfileUrl: String?,
)
