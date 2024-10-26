package com.example.pece2

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pece2.clases.Postulante
import com.example.pece2.controlador.ArregloPostulante

@Preview(showBackground = true)
@Composable
fun NuevoPostulanteActivity(navController: NavController = rememberNavController()) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("Masculino") }
    var estadoCivil by remember { mutableStateOf("") }
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFF9FB)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "POSTULANTE",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(text = "Nombre", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre",
                    color = Color.Black
                ) },
                keyboardOptions = KeyboardOptions
                    (keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                )
            )
            Text(text = "Apellido", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))

            // Campo de texto para apellido
            OutlinedTextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido",
                    color = Color.Black
                ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                )
            )

            Text(text = "DNI", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
            // Campo de texto para DNI
            OutlinedTextField(
                value = dni,
                onValueChange = { dni = it },
                label = { Text("DNI",
                    color = Color.Black) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                )
            )

            // Selección de Sexo
            Text(text = "Sexo", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                RadioButton(
                    selected = sexo == "Masculino",
                    onClick = { sexo = "Masculino" }
                )
                Text(text = "Masculino", modifier = Modifier.padding(end = 16.dp))
                RadioButton(
                    selected = sexo == "Femenino",
                    onClick = { sexo = "Femenino" }
                )
                Text(text = "Femenino", modifier = Modifier.padding(end = 16.dp))
                RadioButton(
                    selected = sexo == "Otros",
                    onClick = { sexo = "Otros" }
                )
                Text(text = "Otros")
            }

            // Campo de texto para Estado Civil con un Dropdown
            var expanded by remember { mutableStateOf(false) }
            val listaEstadoCivil = listOf("Soltero", "Casado", "Divorciado", "Viudo")

            Text(text = "Estado Civil", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
            OutlinedTextField(
                value = estadoCivil,
                onValueChange = { estadoCivil = it },
                label = { Text("Estado Civil") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                    }
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listaEstadoCivil.forEach { civil ->
                    DropdownMenuItem(onClick = {
                        estadoCivil = civil
                        expanded = false
                    }) {
                        Text(text = civil)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para grabar
            Button(
                onClick = {
                    if (nombre.isNotEmpty() && apellido.isNotEmpty() && dni.isNotEmpty()) {
                        val arregloPostulante = ArregloPostulante(context)
                        val nuevoPostulante = Postulante(
                            nombre = nombre,
                            apellido = apellido,
                            dni = dni,
                            edad = 25,  // Puedes agregar un campo para la edad si lo necesitas
                            sexo = sexo,
                            estadoCivil = estadoCivil,
                            imagen = "" // La imagen se asignará aleatoriamente en ArregloPostulante
                        )
                        arregloPostulante.insertarPostulante(nuevoPostulante)
                        Toast.makeText(context, "Postulante registrado correctamente", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "GRABAR",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}