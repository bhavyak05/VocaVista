package com.example.vocavista

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class HomeFragment : Fragment(R.layout.activity_home) {

    private lateinit var welcomeTextView: TextView // Declare TextView for welcome message

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the TextView and Button from the layout
        welcomeTextView = view.findViewById(R.id.tvWelcome) // Assuming the TextView ID is 'tvWelcome'
        val button: Button = view.findViewById(R.id.button) // Assuming the Button ID is 'button'

        // Retrieve the name from SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("name", "User") ?: "User"

        // Set the welcome message
        welcomeTextView.text = "Welcome, $userName" // Display the name in TextView

        // Set an onClickListener for the button to load the QuizFragment
        button.setOnClickListener {
            (activity as HoldActivity).loadFragment(QuizFragment()) // Load the QuizFragment
        }
    }
}
