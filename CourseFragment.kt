import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vocavista.CartFragment
import com.example.vocavista.CartSingleton
import com.example.vocavista.CourseAdapter
import com.example.vocavista.R

class CourseFragment : Fragment(R.layout.activity_course) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CourseAdapter

    private val cart = mutableListOf<Course>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setting up the BottomNavigationView


        // Setting up RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewCourses)
        adapter = CourseAdapter(getCourses()) { course ->
            addToCart(course)
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        // Set the OnClickListener for the "View Cart" button
        val openCartButton = view.findViewById<Button>(R.id.openCartButton)
        openCartButton.setOnClickListener {
            openCart(view)
        }
    }

    private fun getCourses(): List<Course> {
        return listOf(
            Course("1", "Mobile App Development", "Learn mobile app development with easy implementation", 100.0, R.drawable.img1),
            Course("2", "Web Development", "Delve into the world of web development with conducive videos", 120.0, R.drawable.img2),
            Course("3", "Python Programming", "Learn python programming and all its libraries", 90.0, R.drawable.img3)
        )
    }


    fun openCart(view: View) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainer, CartFragment())
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun addToCart(course: Course) {
        CartSingleton.addToCart(course)
        Toast.makeText(requireContext(), "${course.name} added to cart", Toast.LENGTH_SHORT).show()
    }
}
