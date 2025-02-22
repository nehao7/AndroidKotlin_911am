package com.o7services.androidkotlin_9_11am.DrawerLayout

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.o7services.androidkotlin_9_11am.R
import com.o7services.androidkotlin_9_11am.databinding.ActivityNavDrawerBinding

class NavDrawerActivity : AppCompatActivity() {
    lateinit var binding: ActivityNavDrawerBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityNavDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        navController = findNavController(R.id.jetpackNav)
        drawerLayout = findViewById(R.id.main)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,
            R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        setupActionBar()

        actionBarDrawerToggle.syncState()

        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_First->{
                    navController.navigate(R.id.fragmentOne3)
                }
                R.id.nav_Second -> {
                    navController.navigate(R.id.fragmentTwo2)
                }
                R.id.nav_Third -> {
                    navController.navigate(R.id.fragmentThree)
                }

                else -> {
                    // Handle other cases
                }
            }
            // Close the drawer after item is clicked
            drawerLayout.closeDrawers()

            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val actionBar: ActionBar? = supportActionBar
            actionBar?.let {
                it.title = destination.label
                actionBarDrawerToggle.syncState()

            }
        }


    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)  // Show the home button (hamburger)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.white)))
        supportActionBar?.setElevation(0f)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        }else{
            super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return  super.onSupportNavigateUp()|| navController.popBackStack()
    }
}