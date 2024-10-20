package com.example.pece2.adaptador

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.pece2.clases.Postulante

@Composable
fun AdaptadorPostulante(listaPostulantes: List<Postulante>) {
    LazyColumn {
        items(listaPostulantes) { postulante ->
            VistaPostulante(postulante = postulante)
        }
    }
}