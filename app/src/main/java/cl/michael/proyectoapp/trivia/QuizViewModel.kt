package cl.michael.proyectoapp.trivia

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Imports que faltaban
import cl.michael.proyectoapp.trivia.Question
import cl.michael.proyectoapp.trivia.QuizUiState

class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        QuizUiState(
            questions = seedQuestions()
        )
    )

    /**
     * "Solo lectura"
     * UI-->
     * uiState.value = ...ABC
     * */
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun onSelectOption(index: Int) {
        Log.d("SelectOption", "Click en un Option")
        // tomar el estado actual
        val current = _uiState.value

        // Si el usuario termino la trivia
        if (current.isFinished) return

        // Actualizar el estado
        _uiState.value = current.copy(selectedIndex = index)
    }


    private fun seedQuestions() : List<Question> {
        return listOf(
            Question(
                id = 1,
                title = "¿Que palabra clave se usa para declarar una variable inmutable en Kotlin?",
                options = listOf("var", "val", "let", "const"),
                correctIndex = 1
            ),
            Question(
                id = 2,
                title = "En Jetpack Compose, ¿que anotacion marca una funcion como UI?",
                options = listOf("@UI", "@Widget", "@Composable", "@Compose"),
                correctIndex = 2
            ),
            Question(
                id = 3,
                title = "¿Que componente se usa para listas eficientes y scrolleables?",
                options = listOf("Column", "RecyclerView", "Stack", "LazyColumn"),
                correctIndex = 3
            ),
            Question(
                id = 4,
                title = "La instrucción que permite restaurar estado tras recreación de Activity es",
                options = listOf(
                    "intentData",
                    "savedInstanceState",
                    "activityState",
                    "bundleConfig"
                ),
                correctIndex = 1
            ),
        )
    }
}
