package com.example.freegamesapi.model

import java.util.Date

class Juego(
    var id: Int,
    var nombre: String,
    var descripcion: String,
    var urlDescarga: String,
    var fechaLanzamiento: Date,
    var desarrollador: String,
    var imagen: String
) {
    fun obtenerDetalles(): String {
        return "ID: $id, Nombre: $nombre, Descripción: $descripcion, URL: $urlDescarga, Fecha: $fechaLanzamiento, Desarrollador: $desarrollador, Imagen: $imagen"
    }
}
