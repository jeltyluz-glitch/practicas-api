package com.example.freegamesapi.data.remote

import com.google.gson.annotations.SerializedName

/**
 * DTO para las ofertas de CheapShark (https://apidocs.cheapshark.com/).
 */
data class DealDto(
    @SerializedName("dealID") val dealId: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("salePrice") val salePrice: String?,
    @SerializedName("normalPrice") val normalPrice: String?,
    @SerializedName("savings") val savings: String?,
    @SerializedName("thumb") val thumb: String?,
)
