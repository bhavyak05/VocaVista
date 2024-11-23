package com.example.vocavista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Setup UI elements
        val btnSignUpWithEmail = findViewById<Button>(R.id.btnSignUpWithEmail)
        val btnSignUpWithPhone = findViewById<Button>(R.id.btnSignUpWithPhone)
        val btnSignUpWithGoogle = findViewById<Button>(R.id.btnSignUpWithGoogle)
        val tvLoginPrompt = findViewById<TextView>(R.id.tvLoginPrompt)

        // Setup button click listeners
        btnSignUpWithEmail.setOnClickListener {
            // Handle sign up with email
        }
        btnSignUpWithPhone.setOnClickListener {
            // Handle sign up with phone
        }
        btnSignUpWithGoogle.setOnClickListener {
            // Handle sign up with Google
        }
        tvLoginPrompt.setOnClickListener {
            // Go to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
