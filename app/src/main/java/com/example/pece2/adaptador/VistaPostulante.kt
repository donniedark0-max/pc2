package com.example.pece2.adaptador

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.pece2.clases.Postulante

@Composable
fun VistaPostulante(postulante: Postulante) {
    // Horizontal line
    Divider(
        color = Color.LightGray, // Customize the color as needed
        thickness = 1.dp, // Customize the thicknessas needed
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // Add padding if desired
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = "CÓDIGO: ${postulante.id}", fontWeight = FontWeight.Bold)
                Text(text = "NOMBRE: ${postulante.nombre}", fontWeight = FontWeight.SemiBold)
                Text(text = "APELLIDO: ${postulante.apellido}", fontWeight = FontWeight.SemiBold)
            }

            // Mostrar la imagen correspondiente al postulante
            Image(
                painter = rememberImagePainter(data = postulante.imagen),
                contentDescription = "Imagen del postulante",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop // Ajusta la escala de la imagen
            )
        }
    }
}

@Preview
@Composable
fun VistaPostulantePreview() {
    VistaPostulante(
        postulante = Postulante(
            id = 1,
            nombre = "Juan",
            apellido = "Perez",
            dni = "12345678",
            edad = 25,
            sexo = "Masculino",
            estadoCivil = "Soltero",
            imagen = "https://linkaimagen.com"
        )
    )
}