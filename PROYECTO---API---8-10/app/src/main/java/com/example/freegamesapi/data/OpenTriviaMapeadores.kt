package com.example.freegamesapi.data

import android.text.Html
import com.example.freegamesapi.data.remote.TriviaQuestionDto
import com.example.freegamesapi.model.PreguntaTrivia

object OpenTriviaMapeadores {

    fun triviaDtoToPregunta(dto: TriviaQuestionDto): PreguntaTrivia {
        val preguntaLimpia = dto.question?.let {
            Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY).toString()
        } ?: "Sin pregunta"

        val respuestaLimpia = dto.correctAnswer?.let {
            Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY).toString()
        } ?: ""

        val incorrectasLimpias = dto.incorrectAnswers?.map {
            Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY).toString()
        } ?: emptyList()

        return PreguntaTrivia(
            pregunta = preguntaLimpia,
            respuestaCorrecta = respuestaLimpia,
            respuestasIncorrectas = incorrectasLimpias,
        )
    }

    fun listTriviaDtoToListPregunta(dtos: List<TriviaQuestionDto>): List<PreguntaTrivia> {
        return dtos.map { triviaDtoToPregunta(it) }
    }
}
