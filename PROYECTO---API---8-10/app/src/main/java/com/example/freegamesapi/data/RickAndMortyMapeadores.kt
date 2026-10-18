package com.example.freegamesapi.data

import com.example.freegamesapi.data.remote.RickAndMortyCharacterDto
import com.example.freegamesapi.model.PersonajeModel

object RickAndMortyMapeadores {
    fun characterDtoToPersonaje(dto: RickAndMortyCharacterDto): PersonajeModel {
        return PersonajeModel(
            id = dto.id,
            nombre = dto.name ?: "Desconocido",
            estado = dto.status ?: "Desconocido",
            especie = dto.species ?: "Desconocida",
            imagen = dto.image ?: ""
        )
    }

    fun listCharacterDtoToListPersonaje(dtos: List<RickAndMortyCharacterDto>): List<PersonajeModel> {
        return dtos.map { characterDtoToPersonaje(it) }
    }
}
