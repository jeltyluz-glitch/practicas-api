package com.example.freegamesapi.model

class Oferta(
    var dealId: String,
    var titulo: String,
    var precioOferta: String,
    var precioNormal: String,
    var ahorro: String,
    var imagen: String
) {
    fun obtenerDetalles(): String {
        return "Deal: $titulo - Precio: \$$precioOferta (Normal: \$$precioNormal)"
    }
}
