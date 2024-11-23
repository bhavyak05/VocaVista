package com.example.vocavista

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment

class QuizFragment : Fragment() {

    private lateinit var questionTextView: TextView
    private lateinit var option1: TextView
    private lateinit var option2: TextView
    private lateinit var option3: TextView
    private lateinit var option4: TextView
    private lateinit var highScoreTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var currentScoreTextView: TextView

    private val questions = listOf(
        Question("What is the capital of France?", listOf("Paris", "Berlin", "Madrid", "Rome"), 0),
        Question("What is the largest planet in our solar system?", listOf("Earth", "Mars", "Jupiter", "Saturn"), 2),
        Question("What is the chemical symbol for water?", listOf("H2O", "O2", "CO2", "HO"), 0),
        Question("Who wrote 'Romeo and Juliet'?", listOf("Shakespeare", "Hemingway", "Austen", "Dickens"), 0),
        Question("Which country is known as the Land of the Rising Sun?", listOf("China", "Japan", "Thailand", "India"), 1)
    )

    private var currentQuestionIndex = 0
    private var currentScore = 0
    private var highScore = 0
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        questionTextView = view.findViewById(R.id.questionTextView)
        option1 = view.findViewById(R.id.option1)
        option2 = view.findViewById(R.id.option2)
        option3 = view.findViewById(R.id.option3)
        option4 = view.findViewById(R.id.option4)
        highScoreTextView = view.findViewById(R.id.highScoreTextView)
        progressBar = view.findViewById(R.id.progressBar)
        currentScoreTextView = view.findViewById(R.id.currentScoreTextView)

        // Retrieve shared preferences
        sharedPreferences = requireActivity().getSharedPreferences("QUIZ_APP", Context.MODE_PRIVATE)
        highScore = sharedPreferences.getInt("HIGH_SCORE", 0)
        highScoreTextView.text = "High Score: $highScore"

        loadQuestion()

        // Set click listeners for options
        option1.setOnClickListener { onOptionSelected(it) }
        option2.setOnClickListener { onOptionSelected(it) }
        option3.setOnClickListener { onOptionSelected(it) }
        option4.setOnClickListener { onOptionSelected(it) }
    }

    private fun loadQuestion() {
        if (currentQuestionIndex < questions.size) {
            val question = questions[currentQuestionIndex]

            questionTextView.text = question.text
            option1.text = question.options[0]
            option2.text = question.options[1]
            option3.text = question.options[2]
            option4.text = question.options[3]
            progressBar.progress = ((currentQuestionIndex + 1) * 100) / questions.size
        } else {
            endQuiz()
        }
    }

    private fun onOptionSelected(view: View) {
        val selectedOptionIndex = when (view.id) {
            R.id.option1 -> 0
            R.id.option2 -> 1
            R.id.option3 -> 2
            R.id.option4 -> 3
            else -> -1
        }

        if (selectedOptionIndex == questions[currentQuestionIndex].correctAnswer) {
            currentScore += 10
            currentScoreTextView.text = "Score: $currentScore"
        }

        // Apply animation on option selection
        val animation = AlphaAnimation(0.3f, 1.0f).apply { duration = 300 }
        view.startAnimation(animation)

        currentQuestionIndex++
        loadQuestion()
    }

    private fun endQuiz() {
        if (currentScore > highScore) {
            highScore = currentScore
            highScoreTextView.text = "High Score: $highScore"
            sharedPreferences.edit().putInt("HIGH_SCORE", highScore).apply()
        }
        questionTextView.text = "Quiz Completed! Final Score: $currentScore"
        option1.visibility = View.GONE
        option2.visibility = View.GONE
        option3.visibility = View.GONE
        option4.visibility = View.GONE
        progressBar.progress = 100
    }
}

data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswer: Int
)
