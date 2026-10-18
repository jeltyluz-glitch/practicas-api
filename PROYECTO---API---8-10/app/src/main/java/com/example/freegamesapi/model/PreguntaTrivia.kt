package com.example.freegamesapi.model

class PreguntaTrivia(
    var pregunta: String,
    var respuestaCorrecta: String,
    var respuestasIncorrectas: List<String>
) {
    fun obtenerDetalles(): String {
        return "Pregunta: $pregunta | Correcta: $respuestaCorrecta"
    }
}
