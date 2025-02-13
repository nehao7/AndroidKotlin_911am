package com.o7services.androidkotlin_9_11am.bottomnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.o7services.androidkotlin_9_11am.R
import com.o7services.androidkotlin_9_11am.databinding.ActivityBottomNavigationBinding

class BottomNavigationActivity : AppCompatActivity() {
    lateinit var binding: ActivityBottomNavigationBinding
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.navController)

//        appBarConfiguration = AppBarConfiguration(navController.graph)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.settingsFragment, R.id.chatFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuChat-> navController.navigate(R.id.chatFragment)
                R.id.menuHome-> navController.navigate(R.id.homeFragment)
                R.id.menuSetting-> navController.navigate(R.id.settingsFragment)
            }
            return@setOnItemSelectedListener true
        }

        navController.addOnDestinationChangedListener{_, destination ,_->
            when(destination.id){
                R.id.homeFragment-> binding.bottomNavigation.menu.getItem(0).isChecked = true
                R.id.chatFragment-> binding.bottomNavigation.menu.getItem(1).isChecked = true
                R.id.settingsFragment-> binding.bottomNavigation.menu.getItem(2).isChecked = true
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        println("on support navigation up")
        return super.onSupportNavigateUp() || navController.popBackStack()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuSettings-> Toast.makeText(this, "this is setting", Toast.LENGTH_SHORT).show()
            R.id.menuLogout-> Toast.makeText(this, "this is logout", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.side_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}