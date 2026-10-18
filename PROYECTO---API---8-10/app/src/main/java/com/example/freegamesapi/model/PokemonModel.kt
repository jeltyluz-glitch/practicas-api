package com.example.freegamesapi.model

class PokemonModel(
    var id: Int,
    var nombre: String,
    var imagen: String
) {
    fun obtenerDetalles(): String {
        return "Pokémon: $nombre (ID: $id)"
    }
}
