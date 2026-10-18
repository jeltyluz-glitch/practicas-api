package com.example.freegamesapi.data.remote

import com.google.gson.annotations.SerializedName

data class TriviaResponseDto(
    @SerializedName("response_code") val responseCode: Int,
    @SerializedName("results") val results: List<TriviaQuestionDto>?,
)

data class TriviaQuestionDto(
    @SerializedName("question") val question: String?,
    @SerializedName("correct_answer") val correctAnswer: String?,
    @SerializedName("incorrect_answers") val incorrectAnswers: List<String>?,
)
