package com.example.pece2.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class initBD(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME, null,
    DATABASE_VERSION
){
    companion object {
        const val DATABASE_NAME = "pece6.db"
        const val DATABASE_VERSION = 1
    }
    override fun onCreate(db: SQLiteDatabase) {
        val createTablePostulante = """
            CREATE TABLE postulante (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                apellido TEXT,
                dni TEXT UNIQUE,
                edad INTEGER,
                sexo TEXT,
                estadoCivil TEXT,
                imagen TEXT
            )
        """.trimIndent()
        db.execSQL(createTablePostulante)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS postulante")
        onCreate(db)

    }

}