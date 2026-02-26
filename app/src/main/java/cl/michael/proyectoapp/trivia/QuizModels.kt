package cl.michael.proyectoapp.trivia

data class Question(
    val id: Int,
    val title: String,
    val options: List<String>,
    val correctIndex: Int
)

data class QuizUiState(
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val selectedIndex: Int? = null,
    val score: Int = 0,
    val isFinished: Boolean = false,
    val feedbackMessage: String? = null // Nueva variable para el feedback
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentIndex)

    // Útil para saber si estamos en la última pregunta
    val isLastQuestion: Boolean
        get() = currentIndex == questions.size - 1
}
