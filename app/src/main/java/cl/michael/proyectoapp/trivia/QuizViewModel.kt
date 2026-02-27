package cl.michael.proyectoapp.trivia

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        QuizUiState(questions = seedQuestions())
    )
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun resetQuiz() {
        _uiState.value = QuizUiState(questions = seedQuestions())
    }

    fun onSelectOption(index: Int) {
        val current = _uiState.value
        if (current.isFinished || current.feedbackMessage != null) return
        _uiState.value = current.copy(selectedIndex = index)
    }

    fun onConfirmAnswer() {
        val current = _uiState.value
        val selected = current.selectedIndex ?: return
        val currentQuestion = current.currentQuestion ?: return

        if (current.feedbackMessage == null) {
            val isCorrect = selected == currentQuestion.correctIndex
            val feedback = if (isCorrect) "✅ Correcto" else "❌ Incorrecto"
            
            val newScore = if (isCorrect) current.score + 100 else current.score
            val newLives = if (isCorrect) current.lives else current.lives - 1
            
            _uiState.value = current.copy(
                score = newScore,
                lives = newLives,
                feedbackMessage = feedback
            )
        } else {
            // Si el usuario se quedó sin vidas, al presionar continuar termina el juego
            if (current.lives <= 0) {
                _uiState.value = current.copy(isFinished = true)
                return
            }

            val nextIndex = current.currentIndex + 1
            val finished = nextIndex >= current.questions.size

            _uiState.value = current.copy(
                currentIndex = nextIndex,
                selectedIndex = null,
                feedbackMessage = null,
                isFinished = finished
            )
        }
    }

    private fun seedQuestions(): List<Question> {
        return listOf(
            Question(1, "¿Qué palabra clave define una constante en tiempo de compilación?", listOf("val", "var", "const val", "final"), 2),
            Question(2, "¿Cuál es el tipo de retorno de una función que no devuelve nada útil?", listOf("Void", "Null", "Nothing", "Unit"), 3),
            Question(3, "¿Cómo se llama el operador ?: en Kotlin?", listOf("Elvis", "Null Safe", "Safe Call", "Ternario"), 0),
            Question(4, "¿Cuál es la diferencia principal entre val y const val?", listOf("Ninguna", "const val es solo para primitivos/String", "val es más rápida", "const val es para variables"), 1),
            Question(5, "¿Qué función de alcance (scope function) devuelve el resultado de la última línea?", listOf("apply", "also", "let", "run"), 2),
            Question(6, "¿Cómo se define un Singleton en Kotlin?", listOf("class", "object", "static class", "instance"), 1),
            Question(7, "¿Qué anotación se usa para llamar código Kotlin desde Java sin 'Companion'?", listOf("@JvmStatic", "@JvmField", "@JvmOverloads", "@JvmName"), 0),
            Question(8, "¿Qué es una 'data class'?", listOf("Una clase para base de datos", "Una clase que genera automáticamente equals/hashCode/copy", "Una clase sin métodos", "Una interfaz"), 1),
            Question(9, "¿Cómo se hace una 'Safe Call'?", listOf("v!!", "v?", "v!.", "v?."), 3),
            Question(10, "¿Qué palabra clave se usa para heredar de una clase o implementar interfaz?", listOf("extends", "implements", ":", "->"), 2),
            Question(11, "¿Qué hace la función 'lazy'?", listOf("Retrasa la ejecución 1s", "Inicializa la variable solo cuando se usa", "Es una corrutina", "Limpia la memoria"), 1),
            Question(12, "¿Cuál es la clase base de todas las clases en Kotlin?", listOf("Object", "Any", "Base", "Root"), 1),
            Question(13, "¿Qué significa que sea 'open' una clase?", listOf("Que es pública", "Que puede ser heredada", "Que no tiene cuerpo", "Que es una interfaz"), 1),
            Question(14, "¿Qué operador se usa para comparar igualdad estructural (valor)?", listOf("==", "===", "=", ".equals"), 0),
            Question(15, "¿Para qué sirve el modificador 'lateinit'?", listOf("Variables inmutables", "Variables que se inicializan después", "Variables opcionales", "Variables estáticas"), 1)
        ).shuffled()
    }
}
