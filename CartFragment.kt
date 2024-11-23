package com.example.vocavista

import Course
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartFragment : Fragment(R.layout.activity_cart) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter
    private lateinit var totalAmountText: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        totalAmountText = view.findViewById(R.id.cartTotalAmount)
        recyclerView = view.findViewById(R.id.cartRecyclerView)

        // Initialize adapter with CartSingleton.cart data
        adapter = CartAdapter(CartSingleton.cart.toMutableList(), ::removeFromCart)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        calculateTotal()

        // Handle Proceed to Payment button click
        view.findViewById<View>(R.id.proceedToPaymentButton).setOnClickListener {
            proceedToPayment()
        }
    }

    private fun calculateTotal() {
        val totalAmount = CartSingleton.calculateTotal()
        totalAmountText.text = "Total: $$totalAmount"
    }

    private fun removeFromCart(course: Course) {
        CartSingleton.removeFromCart(course)  // Remove item from CartSingleton
        adapter.removeItem(course)  // Update adapter with the removed item
        calculateTotal()  // Recalculate the total
        Toast.makeText(requireContext(), "${course.name} removed from cart", Toast.LENGTH_SHORT).show()
    }


    private fun proceedToPayment() {
        val intent = Intent(requireContext(), PaymentActivity::class.java)
        intent.putParcelableArrayListExtra("cart", ArrayList(CartSingleton.cart))
        startActivity(intent)
    }
}
