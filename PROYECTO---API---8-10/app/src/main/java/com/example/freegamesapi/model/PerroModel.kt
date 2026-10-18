package com.example.freegamesapi.model

class PerroModel(
    var id: Int,
    var urlImagen: String,
    var raza: String
) {
    fun obtenerDetalles(): String {
        return "Perro: $raza"
    }
}
