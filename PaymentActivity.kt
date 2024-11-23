package com.example.vocavista

import Course
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PaymentActivity : AppCompatActivity() {
    private var cart: List<Course> = listOf()
    private lateinit var totalAmountText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        cart = intent.getParcelableArrayListExtra("cart") ?: listOf()
        totalAmountText = findViewById(R.id.totalAmount)

        val totalAmount = cart.sumOf { it.price }
        totalAmountText.text = "Total: $$totalAmount"

        findViewById<Button>(R.id.payButton).setOnClickListener {
            // Handle payment logic here
            Toast.makeText(this, "Payment successful!", Toast.LENGTH_SHORT).show()
        }
    }
}
