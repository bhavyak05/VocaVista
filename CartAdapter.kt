package com.example.vocavista

import Course
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val cartItems: MutableList<Course>,
    private val onRemoveClick: (Course) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseImage: ImageView = itemView.findViewById(R.id.courseImage)
        val courseName: TextView = itemView.findViewById(R.id.courseName)
        val coursePrice: TextView = itemView.findViewById(R.id.coursePrice)
        val removeButton: Button = itemView.findViewById(R.id.removeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart_course, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val course = cartItems[position]

        // Set course name and price
        holder.courseName.text = course.name
        holder.coursePrice.text = "Price: $${course.price}"

        // Set the course image directly from drawable resource ID
        holder.courseImage.setImageResource(course.imageResId)

        // Set up the "Remove" button click listener
        holder.removeButton.setOnClickListener {
            onRemoveClick(course)
        }
    }

    override fun getItemCount(): Int = cartItems.size

    fun removeItem(course: Course) {
        val position = cartItems.indexOf(course)
        if (position != -1) {
            cartItems.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartItems.size)
        }
    }
}
