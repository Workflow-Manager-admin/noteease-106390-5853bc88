package com.example.notesfrontend

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.notesfrontend.viewmodel.NotesViewModel

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

/**
 * MainActivity for the Notes app, hosts the navigation.
 */
class MainActivity : AppCompatActivity() {
    private val notesViewModel: NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        // Defensive: Support older fragment manager API and null check
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (navHostFragment is NavHostFragment) {
            setupActionBarWithNavController(navHostFragment.navController)
        } else if (navHostFragment == null) {
            throw IllegalStateException("NavHostFragment not found. Ensure that activity_main.xml contains a fragment with id 'nav_host_fragment'.")
        } else {
            throw IllegalStateException("Fragment with id 'nav_host_fragment' is not a NavHostFragment.")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (navHostFragment is NavHostFragment) {
            return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
        }
        return super.onSupportNavigateUp()
    }
}
