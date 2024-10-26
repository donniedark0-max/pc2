package com.example.pece2

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.pece2.controlador.ArregloPostulante


@Composable
fun DetallePostulanteActivity(postulanteId: Int, navController: NavController) {
    val context = LocalContext.current
    val arregloPostulante = ArregloPostulante(context)
    val postulante = remember { arregloPostulante.obtenerPostulantePorID(postulanteId) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFF9FB)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),

        ) {
            Text(
                text = "DETALLE POSTULANTE",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            if (postulante != null) {
                Spacer(modifier = Modifier.height(8.dp))

                DetailRow("Código", postulante.id.toString(), Color.Red)
                DetailRow("Nombre", postulante.nombre, Color.Red)
                DetailRow("Apellido", postulante.apellido, Color.Red)
                DetailRow("DNI", postulante.dni, Color.Red)
                DetailRow("Sexo", postulante.sexo, Color.Red)
                DetailRow("Estado Civil", postulante.estadoCivil, Color.Red)

                Spacer(modifier = Modifier.height(24.dp))

                // Imagen del postulante (simulada)
                Image(
                    painter = painterResource(id = postulante.imagen.toInt()), // Convertir el string a ID si es necesario
                    contentDescription = "Imagen del postulante",
                    modifier = Modifier
                        .size(180.dp)
                        .padding(16.dp)
                )
            } else {
                Text(
                    text = "No se encontró al postulante",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String, labelColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        Text(
            text = label,
            color = labelColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}