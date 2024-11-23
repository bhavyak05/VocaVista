package com.example.vocavista

import CourseFragment
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HoldActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hold)

        // Set up Toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Initialize DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout)

        // Enable the drawer button in the toolbar
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_user) // Use a menu icon drawable here
        }

        // Load HomeFragment by default
        loadFragment(HomeFragment())

        // Set up BottomNavigationView
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> loadFragment(HomeFragment())
                R.id.navigation_course -> loadFragment(CourseFragment())
                R.id.navigation_dashboard -> loadFragment(DashboardFragment())
                R.id.navigation_roadmap -> loadFragment(RoadmapFragment())
                else -> false
            }
            true
        }

        // Set up NavigationView for Drawer
        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_settings -> loadFragment(SettingsFragment())
                R.id.cart -> loadFragment(CartFragment())
                R.id.nav_contactus -> {
                    // Create an intent to open the email application
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:vocavistasupport@gmail.com")
                        putExtra(Intent.EXTRA_SUBJECT, "Support Request")
                    }
                    try {
                        startActivity(emailIntent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(this, "No email client installed.", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.nav_rateus -> {
                    // Directly open Play Store for rating the app
                    val uri = Uri.parse("https://play.google.com/store")
                    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                    try {
                        startActivity(goToMarket)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(this, "Unable to open Play Store.", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.profile -> loadFragment(ProfileFragment())
                R.id.logout -> {
                    // Navigate back to login page
                    val intent = Intent(this, Log::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish() // End current activity to prevent back navigation to it
                }

                else -> false
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    // Handle opening the drawer on toolbar button click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            true
        } else super.onOptionsItemSelected(item)
    }

    // Function to load the fragment
    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
