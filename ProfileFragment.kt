package com.example.vocavista

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.io.ByteArrayOutputStream

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var sharedPreferences: SharedPreferences
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_GALLERY = 3
    private lateinit var profileImageView: ImageView
    private lateinit var saveButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var dobEditText: EditText
    private lateinit var genderEditText: EditText
    private lateinit var phoneEditText: EditText
    private var selectedImageUri: Uri? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize views
        profileImageView = view.findViewById(R.id.profile_image)
        saveButton = view.findViewById(R.id.save_button)
        nameEditText = view.findViewById(R.id.name_edittext)
        ageEditText = view.findViewById(R.id.age_edittext)
        dobEditText = view.findViewById(R.id.dob_edittext)
        genderEditText = view.findViewById(R.id.gender_edittext)
        phoneEditText = view.findViewById(R.id.phone_edittext)

        // Shared Preferences initialization
        sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // Load saved data into EditTexts
        nameEditText.setText(sharedPreferences.getString("name", ""))
        ageEditText.setText(sharedPreferences.getString("age", ""))
        dobEditText.setText(sharedPreferences.getString("dob", ""))
        genderEditText.setText(sharedPreferences.getString("gender", ""))
        phoneEditText.setText(sharedPreferences.getString("phone", ""))

        // Load saved profile image
        val savedImage = sharedPreferences.getString("profile_image", null)
        if (savedImage != null) {
            val imageBytes = Base64.decode(savedImage, Base64.DEFAULT)
            val decodedBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            profileImageView.setImageBitmap(decodedBitmap)
        }

        // Camera/Gallery button click
        view.findViewById<Button>(R.id.camera_button).setOnClickListener {
            showImagePickerDialog()
        }

        // Save button click
        saveButton.setOnClickListener {
            saveProfileData()
        }

        return view
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Camera", "Gallery")
        AlertDialog.Builder(requireContext())
            .setTitle("Choose Image Source")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> dispatchTakePictureIntent()  // Camera option
                    1 -> openGallery()  // Gallery option
                }
            }.show()
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as? Bitmap
                    profileImageView.setImageBitmap(imageBitmap)
                    saveImageToSharedPreferences(imageBitmap)
                }
                REQUEST_IMAGE_GALLERY -> {
                    selectedImageUri = data?.data
                    profileImageView.setImageURI(selectedImageUri)
                    selectedImageUri?.let { uri ->
                        val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                        saveImageToSharedPreferences(bitmap)
                    }
                }
            }
        }
    }

    private fun saveImageToSharedPreferences(bitmap: Bitmap?) {
        bitmap?.let {
            val baos = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val imageBytes = baos.toByteArray()
            val encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            sharedPreferences.edit().putString("profile_image", encodedImage).apply()
        }
    }

    private fun saveProfileData() {
        if (nameEditText.text.isNullOrBlank() || ageEditText.text.isNullOrBlank()) {
            Toast.makeText(requireContext(), "Name and Age are required!", Toast.LENGTH_SHORT).show()
            return
        }
        sharedPreferences.edit()
            .putString("name", nameEditText.text.toString())
            .putString("age", ageEditText.text.toString())
            .putString("dob", dobEditText.text.toString())
            .putString("gender", genderEditText.text.toString())
            .putString("phone", phoneEditText.text.toString())
            .apply()
        Toast.makeText(requireContext(), "Profile saved successfully!", Toast.LENGTH_SHORT).show()
    }

}
