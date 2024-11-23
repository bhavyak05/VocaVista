package com.example.vocavista



import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class AccountSettingsFragment : Fragment(R.layout.fragment_account_settings) {

    private lateinit var emailEditText: EditText
    private lateinit var contactNumberEditText: EditText
    private lateinit var saveButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account_settings, container, false)

        // Initialize fields
        emailEditText = view.findViewById(R.id.edit_text_email)
        contactNumberEditText = view.findViewById(R.id.edit_text_contact_number)
        saveButton = view.findViewById(R.id.button_save_changes)

        // Set listener for the Save button
        saveButton.setOnClickListener {
            val newEmail = emailEditText.text.toString()
            val newContactNumber = contactNumberEditText.text.toString()

            if (newEmail.isNotEmpty() && newContactNumber.isNotEmpty()) {
                Toast.makeText(requireContext(), "Changes saved successfully", Toast.LENGTH_SHORT).show()
                // Implement saving changes to database or backend here
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
