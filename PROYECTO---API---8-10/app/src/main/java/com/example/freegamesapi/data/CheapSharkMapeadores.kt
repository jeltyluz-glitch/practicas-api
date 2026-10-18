package com.example.freegamesapi.data

import com.example.freegamesapi.data.remote.DealDto
import com.example.freegamesapi.model.Oferta

object CheapSharkMapeadores {

    fun dealDtoToOferta(dto: DealDto): Oferta {
        return Oferta(
            dealId = dto.dealId ?: "",
            titulo = dto.title ?: "Sin título",
            precioOferta = dto.salePrice ?: "0.00",
            precioNormal = dto.normalPrice ?: "0.00",
            ahorro = dto.savings ?: "0.0",
            imagen = dto.thumb ?: "",
        )
    }

    fun listDealDtoToListOferta(dtos: List<DealDto>): List<Oferta> {
        return dtos.map { dealDtoToOferta(it) }
    }
}
