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
    val lives: Int = 3,
    val isFinished: Boolean = false,
    val feedbackMessage: String? = null 
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentIndex)

    val isLastQuestion: Boolean
        get() = currentIndex == questions.size - 1

    val progressPercentage: Int
        get() = if (questions.isEmpty()) 0 else (currentIndex * 100) / questions.size
}
