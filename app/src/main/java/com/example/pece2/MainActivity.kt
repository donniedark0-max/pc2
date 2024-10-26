package com.example.pece2
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pece2.adaptador.AdaptadorPostulante
import com.example.pece2.controlador.ArregloPostulante
import com.example.pece2.data.initBD
import com.example.pece2.ui.theme.Pece2Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Establecer el contenido de la actividad utilizando Jetpack Compose
        setContent {
            // Aquí puedes usar el tema que has definido en tu proyecto, si tienes uno
            Pece2Theme {
                val navController: NavHostController = rememberNavController()
                MainActivityContent(navController)
            }
        }
    }
}

@Composable
fun MainActivityContent(navController: NavHostController) {
    // Este será el contenido de tu MainActivity, utilizando Jetpack Compose
    NavHost(navController = navController, startDestination = "MainScreen") {
        // Definir la pantalla principal
        composable("MainScreen") {
            MainScreen(navController) // La función que muestra la pantalla principal
        }

        // Definir la pantalla para agregar un nuevo postulante
        composable("NuevoPostulanteScreen") {
            NuevoPostulanteActivity(navController) // La función que muestra la pantalla para agregar un postulante
        }
        composable("DetallePostulanteScreen/{postulanteId}") { backStackEntry ->
            val postulanteId = backStackEntry.arguments?.getString("postulanteId")?.toInt()
            if (postulanteId != null) {
                DetallePostulanteActivity(postulanteId = postulanteId, navController = navController)
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val arregloPostulante = ArregloPostulante(context)
    var apellidoFiltro by remember { mutableStateOf("") }
    var listaPostulantes by remember { mutableStateOf(listOf<Postulante>()) }
    val dbHelper = initBD(context)
    val coroutineScope = rememberCoroutineScope()

    // Cargar la lista de postulantes cuando la pantalla se inicializa
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            listaPostulantes = arregloPostulante.obtenerTodosLosPostulantes() // Cargar postulantes desde la base de datos
        }
    }
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
            AdaptadorPostulante(listaPostulantes, navController = navController)
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