package com.example.pece2.adaptador

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.pece2.clases.Postulante

@Composable
fun AdaptadorPostulante(listaPostulantes: List<Postulante>, navController: NavHostController) {
    LazyColumn {
        items(listaPostulantes) { postulante ->
            VistaPostulante(postulante = postulante, onClick = {
                // Navegar a la pantalla de detalles pasando el id del postulante
                navController.navigate("DetallePostulanteScreen/${postulante.id}")
            })
        }
    }
}