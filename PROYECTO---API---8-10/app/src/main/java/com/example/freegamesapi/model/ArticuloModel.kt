package com.example.freegamesapi.model

class ArticuloModel(
    var id: Int,
    var titulo: String,
    var resumen: String,
    var fuente: String,
    var imagen: String
) {
    fun obtenerDetalles(): String {
        return "Artículo: $titulo ($fuente)"
    }
}
