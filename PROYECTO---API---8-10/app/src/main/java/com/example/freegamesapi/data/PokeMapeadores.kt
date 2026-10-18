package com.example.freegamesapi.data

import com.example.freegamesapi.data.remote.PokeResultDto
import com.example.freegamesapi.model.PokemonModel

object PokeMapeadores {
    fun pokeDtoToPokemon(dto: PokeResultDto): PokemonModel {
        val id = dto.url?.trimEnd('/')?.substringAfterLast('/')?.toIntOrNull() ?: 1
        val imagen = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

        return PokemonModel(
            id = id,
            nombre = dto.name?.replaceFirstChar { it.uppercase() } ?: "Desconocido",
            imagen = imagen
        )
    }

    fun listPokeDtoToListPokemon(dtos: List<PokeResultDto>): List<PokemonModel> {
        return dtos.map { pokeDtoToPokemon(it) }
    }
}
