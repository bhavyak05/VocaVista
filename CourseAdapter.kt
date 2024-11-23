package com.example.vocavista

import Course
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseAdapter(
    private val courseList: List<Course>,
    private val onAddToCartClicked: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val courseImage: ImageView = itemView.findViewById(R.id.courseImage)
        private val courseName: TextView = itemView.findViewById(R.id.courseName)
        private val courseDescription: TextView = itemView.findViewById(R.id.courseDescription)
        private val coursePrice: TextView = itemView.findViewById(R.id.coursePrice)
        private val addToCartButton: Button = itemView.findViewById(R.id.addToCartButton)

        fun bind(course: Course) {
            courseName.text = course.name
            courseDescription.text = course.description
            coursePrice.text = "Price: $${course.price}"
            courseImage.setImageResource(course.imageResId)  // Load image resource

            addToCartButton.setOnClickListener {
                onAddToCartClicked(course)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courseList[position])
    }

    override fun getItemCount() = courseList.size
}
