package cl.michael.proyectoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cl.michael.proyectoapp.ui.theme.ProyectoappTheme

class TodoAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoappTheme {
                TodoApp(
                    onBack = { finish() }
                )
            }
        }
    }
}

data class TodoItem(
    val id: Long,
    val text: String,
    val done: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp(onBack: () -> Unit) {

    var newTodoText by rememberSaveable { mutableStateOf("") }

    val todos = remember {
        mutableStateListOf(
            TodoItem(id = 1L, text = "Aprender Kotlin", done = false),
            TodoItem(id = 2L, text = "Finalizar el curso")
        )
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Todo App",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton( onClick = onBack ) {
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
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (newTodoText.isNotBlank()) {
                    val newTodo = TodoItem(
                        id = System.currentTimeMillis(),
                        text = newTodoText
                    )
                    todos.add(newTodo)
                    newTodoText = ""
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar tarea")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
           OutlinedTextField(
               value = newTodoText,
               onValueChange = { newTodoText = it },
               label = { Text("Nueva tarea") },
               modifier = Modifier.fillMaxWidth()
           )

            Text("Listado", style = MaterialTheme.typography.titleMedium)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                items(todos, key = { it.id } ) { task ->

                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val index = todos.indexOf(task)
                                if (index != -1) {
                                    todos[index] = task.copy(done = !task.done)
                                }
                            }
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = task.text,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 4.dp),
                                textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None
                            )

                            IconButton(onClick = { todos.remove(task) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar"
                                )
                            }
                        }

                    }

                }
            }

        }

    }
}
