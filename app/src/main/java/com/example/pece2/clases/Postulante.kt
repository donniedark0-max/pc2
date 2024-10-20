package com.example.pece2.clases

data class Postulante(
    val id: Int = 0,
    var nombre: String,
    var apellido: String,
    var dni: String,
    var edad: Int,
    var sexo: String,
    var estadoCivil: String,
    var imagen: String // Ruta de la imagen
)