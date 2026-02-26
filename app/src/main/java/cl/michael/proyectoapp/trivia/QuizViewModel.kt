package cl.michael.proyectoapp.trivia

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        QuizUiState(
            questions = seedQuestions()
        )
    )

    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun onSelectOption(index: Int) {
        val current = _uiState.value
        // Si ya hay feedback (mostrando resultado), no dejamos cambiar la opción
        if (current.isFinished || current.feedbackMessage != null) return

        _uiState.value = current.copy(selectedIndex = index)
    }

    fun onConfirmAnswer() {
        val current = _uiState.value
        val selected = current.selectedIndex ?: return
        val currentQuestion = current.currentQuestion ?: return

        // Si NO hay feedback, estamos evaluando por primera vez
        if (current.feedbackMessage == null) {
            val isCorrect = selected == currentQuestion.correctIndex
            val feedback = if (isCorrect) "✅ Correcto" else "❌ Incorrecto"
            val newScore = if (isCorrect) current.score + 100 else current.score

            _uiState.value = current.copy(
                score = newScore,
                feedbackMessage = feedback
            )
        } else {
            // Si YA hay feedback, el usuario presionó de nuevo para continuar
            val nextIndex = current.currentIndex + 1
            val finished = nextIndex >= current.questions.size

            _uiState.value = current.copy(
                currentIndex = nextIndex,
                selectedIndex = null,
                feedbackMessage = null, // Limpiamos el feedback para la siguiente
                isFinished = finished
            )
        }
    }

    private fun seedQuestions(): List<Question> {
        return listOf(
            Question(
                id = 1,
                title = "¿Que palabra clave se usa para declarar una variable inmutable en Kotlin?",
                options = listOf("var", "val", "let", "const"),
                correctIndex = 1
            ),
            Question(
                id = 2,
                title = "En Jetpack Compose, ¿que anotación marca una función como UI?",
                options = listOf("@UI", "@Widget", "@Composable", "@Compose"),
                correctIndex = 2
            ),
            Question(
                id = 3,
                title = "¿Qué componente se usa para listas eficientes y scrolleables?",
                options = listOf("Column", "RecyclerView", "Stack", "LazyColumn"),
                correctIndex = 3
            ),
            Question(
                id = 4,
                title = "La instrucción que permite restaurar estado tras recreación de Activity es",
                options = listOf("intentData", "savedInstanceState", "activityState", "bundleConfig"),
                correctIndex = 1
            ),
            Question(
                id = 5,
                title = "¿Cual es el lenguaje oficial recomendado por Google para Android?",
                options = listOf("Java", "C++", "Kotlin", "Python"),
                correctIndex = 2
            ),
            Question(
                id = 6,
                title = "¿Que herramienta de Android Studio permite ver el diseño en tiempo real?",
                options = listOf("Logcat", "Debugger", "Preview", "Terminal"),
                correctIndex = 2
            )
        )
    }
}
