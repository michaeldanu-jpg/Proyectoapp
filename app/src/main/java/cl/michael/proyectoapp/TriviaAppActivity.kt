package cl.michael.proyectoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cl.michael.proyectoapp.ui.theme.ProyectoappTheme

class TriviaAppActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoappTheme {

                var state by remember { mutableStateOf(true) } // Use mutableState for dynamic changes


                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    "Trivia App",
                                    color = Color.White
                                )
                            },

                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                        contentDescription = "Volver",
                                        tint = Color.White
                                    )

                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFF1E88E5)
                            )
                        )
                    },
                ) { innerPadding ->
                    Box(
                        modifier = Modifier // Corrected typo from 'modififier'
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {

                        if (!state) {
                            // vista FinishedScreen
                            FinishedScreen()
                        } else {
                            // vista QuestionScreen
                            QuestionScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Progreso ( 1/5 )
        Text(
            text = "Pregunta 1 de 5",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Pregunta 1 ABC",
            style = MaterialTheme.typography.headlineSmall // Corrected typo from 'MaterialThem' and case from 'headLineSmall'
        )

        repeat(4) { index -> // Added 'index' to use it below
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = false,
                    onClick = {}
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Respuesta ${index + 1} pregunta 1", // Corrected string interpolation
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar") // Corrected typo from 'Comfirmar'
        }
    }
}

@Composable
fun FinishedScreen() {
    // Added a placeholder text
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("¡Trivia finalizada!")
    }
}
