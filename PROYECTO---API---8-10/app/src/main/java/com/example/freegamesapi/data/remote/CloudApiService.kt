package com.example.freegamesapi.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

data class SqlRequest(
    @SerializedName("sql") val sql: String,
    @SerializedName("database") val database: String = "Favoritos"
)

data class SqlResponse(
    @SerializedName("data") val data: Any?,
    @SerializedName("metadata") val metadata: Any?
)

interface CloudApiService {
    @POST("v2/weblite/sql")
    suspend fun ejecutarSql(@Body request: SqlRequest): SqlResponse
}
