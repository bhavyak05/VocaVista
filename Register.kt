package com.example.vocavista

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.regex.Pattern

class Register : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var dobEditText: EditText
    private lateinit var genderSpinner: Spinner
    private lateinit var registerButton: Button
    private lateinit var loginTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nameEditText = findViewById(R.id.editTextName)
        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        ageEditText = findViewById(R.id.editTextAge)
        dobEditText = findViewById(R.id.editTextDOB)
        genderSpinner = findViewById(R.id.spinnerGender)
        registerButton = findViewById(R.id.buttonRegister)
        loginTextView = findViewById(R.id.textViewLogin)

        setupGenderSpinner()
        setupDateOfBirthPicker()

        registerButton.setOnClickListener { registerUser() }
        loginTextView.setOnClickListener { openLog() }
    }

    private fun setupGenderSpinner() {
        val genders = arrayOf("Select Gender", "Male", "Female", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter
    }

    private fun setupDateOfBirthPicker() {
        dobEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = android.app.DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                dobEditText.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
            }, year, month, day)
            datePickerDialog.show()
        }
    }

    private fun registerUser() {
        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val age = ageEditText.text.toString()
        val dob = dobEditText.text.toString()
        val gender = genderSpinner.selectedItem.toString()

        // Validate the fields
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || age.isEmpty() || dob.isEmpty() || gender == "Select Gender") {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidName(name)) {
            Toast.makeText(this, "Name should only contain alphabets and spaces", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "Password must be at least 6 characters long, containing digits, lowercase, uppercase, and special characters", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidAge(age)) {
            Toast.makeText(this, "Age must be a positive number", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isAgeMatchingDOB(age, dob)) {
            Toast.makeText(this, "Age does not match the date of birth", Toast.LENGTH_SHORT).show()
            return
        }

        // Save user data in SharedPreferences
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("email", email)
        editor.putString("password", password)
        editor.putString("age", age)
        editor.putString("dob", dob)
        editor.putString("gender", gender)
        editor.apply()

        Toast.makeText(this, "User Registered: $name", Toast.LENGTH_SHORT).show()
        openLog()
    }

    private fun isValidName(name: String): Boolean {
        val namePattern = "^[a-zA-Z\\s]+$"
        return name.matches(Regex(namePattern))
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        return email.matches(Regex(emailPattern))
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*+=?<>])(?=\\S+\$).{6,}$"
        return password.matches(Regex(passwordPattern))
    }

    private fun isValidAge(age: String): Boolean {
        return try {
            val ageInt = age.toInt()
            ageInt > 0
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun isAgeMatchingDOB(age: String, dob: String): Boolean {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val dobYear = dob.split("/")[2].toInt()

        val calculatedAge = currentYear - dobYear
        return age.toInt() == calculatedAge
    }

    private fun openLog() {
        val intent = Intent(this, Log::class.java)
        startActivity(intent)
    }
}

