package com.example.pece2.controlador

import android.content.ContentValues
import android.content.Context
import com.example.pece2.clases.Postulante
import com.example.pece2.data.initBD
import com.example.pece2.R


class ArregloPostulante(context: Context) {
    private val dbHelper = initBD(context)
    private val imagenesDisponibles = mutableListOf(
        R.drawable.imagen1,
        R.drawable.imagen2,
        R.drawable.imagen3,
        R.drawable.imagen4,
        R.drawable.imagen5
    )

    private val imagenesUsadas = mutableListOf<Int>()

    private fun obtenerImagenAleatoria(): Int {
        if (imagenesDisponibles.isEmpty()) {
            // Si todas las imágenes ya fueron usadas, reseteamos el conjunto
            imagenesDisponibles.addAll(imagenesUsadas)
            imagenesUsadas.clear()
        }
        // Seleccionar una imagen aleatoria
        val imagenSeleccionada = imagenesDisponibles.random()
        imagenesDisponibles.remove(imagenSeleccionada)
        imagenesUsadas.add(imagenSeleccionada)
        return imagenSeleccionada
    }

    // Insertar postulante
    fun insertarPostulante(postulante: Postulante): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", postulante.nombre)
            put("apellido", postulante.apellido)
            put("edad", postulante.edad)
            put("dni", postulante.dni)
            put("sexo", postulante.sexo)
            put("estadoCivil", postulante.estadoCivil)
            put("imagen", obtenerImagenAleatoria()) // Asignar imagen aleatoria
        }
        return db.insert("postulante", null, values)
    }

    // Leer postulantes
    fun obtenerTodosLosPostulantes(): List<Postulante> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM postulante", null)
        val postulantes = mutableListOf<Postulante>()

        while (cursor.moveToNext()) {
            val postulante = Postulante(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad")),
                sexo = cursor.getString(cursor.getColumnIndexOrThrow("sexo")),
                estadoCivil = cursor.getString(cursor.getColumnIndexOrThrow("estadoCivil")),
                imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
            )
            postulantes.add(postulante)
        }
        cursor.close()
        return postulantes
    }

    // Obtener postulante por ID
    fun obtenerPostulantePorID(id: Int): Postulante? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM postulante WHERE id = ?", arrayOf(id.toString()))

        if (cursor.moveToFirst()) {
            val postulante = Postulante(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad")),
                sexo = cursor.getString(cursor.getColumnIndexOrThrow("sexo")),
                estadoCivil = cursor.getString(cursor.getColumnIndexOrThrow("estadoCivil")),
                imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
            )
            cursor.close()
            return postulante
        }
        cursor.close()
        return null
    }
}