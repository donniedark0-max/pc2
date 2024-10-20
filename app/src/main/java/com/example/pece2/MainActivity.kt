package com.example.pece2
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pece2.clases.Postulante
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.text.input.KeyboardType
import com.example.pece2.adaptador.AdaptadorPostulante
import com.example.pece2.data.initBD

@Preview(showBackground = true)
@Composable
fun MainActivity(navController: NavController = rememberNavController()) {
    var apellidoFiltro by remember { mutableStateOf("") }
    var listaPostulantes by remember { mutableStateOf(listOf<Postulante>()) }
    val context = LocalContext.current
    val dbHelper = initBD(context)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFF9FB)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Botón "Nuevo Postulante"
            Button(
                onClick = {
                    // Aquí navegas a la pantalla para agregar un nuevo postulante
                    navController.navigate("NuevoPostulanteScreen")
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EA),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "NUEVO POSTULANTE",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            // Campo de texto para el filtro de apellido
            OutlinedTextField(
                value = apellidoFiltro,
                onValueChange = { apellidoFiltro = it },
                label = { Text("Ingresar apellido") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                trailingIcon = {
                    IconButton(onClick = {
                        // Acción de búsqueda al hacer clic en el ícono
                        listaPostulantes = filtrarPostulantes(apellidoFiltro, context)
                    }) {
                        Icon(Icons.Filled.Search, contentDescription = "Buscar")
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Horizontal line
            Divider(
                color = Color.LightGray, // Customize the color as needed
                thickness = 1.dp, // Customize the thicknessas needed
                modifier = Modifier
                    .fillMaxWidth()
            )

            // Título del listado de postulantes
            Text(
                text = "LISTADO POSTULANTES",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 58.dp)
            )

            // Usar AdaptadorPostulante para mostrar la lista
            AdaptadorPostulante(listaPostulantes)
        }
        }

}



fun filtrarPostulantes(apellido: String, context: Context): List<Postulante> {
    // Implementar la lógica de filtrado en la base de datos SQLite
    val dbHelper = initBD(context)  // Instancia de la base de datos
    val db = dbHelper.readableDatabase
    val cursor = db.rawQuery("SELECT * FROM postulante WHERE apellido LIKE ?", arrayOf("%$apellido%"))
    val postulantes = mutableListOf<Postulante>()
    while (cursor.moveToNext()) {
        val postulante = Postulante(
            cursor.getInt(cursor.getColumnIndexOrThrow("id")),
            cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
            cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
            cursor.getString(cursor.getColumnIndexOrThrow("dni")),
            cursor.getInt(cursor.getColumnIndexOrThrow("edad")),
            cursor.getString(cursor.getColumnIndexOrThrow("sexo")),
            cursor.getString(cursor.getColumnIndexOrThrow("estadoCivil")),
            cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
        )
        postulantes.add(postulante)
    }
    cursor.close()
    return postulantes
}