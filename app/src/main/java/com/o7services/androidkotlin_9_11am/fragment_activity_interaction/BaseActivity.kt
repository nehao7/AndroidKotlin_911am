package com.o7services.androidkotlin_9_11am.fragment_activity_interaction

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.o7services.androidkotlin4_6pmmcpc.fragments.InteractionInterface
import com.o7services.androidkotlin_9_11am.R
import com.o7services.androidkotlin_9_11am.databinding.ActivityBaseBinding


class BaseActivity : AppCompatActivity() {
    lateinit var binding: ActivityBaseBinding
    var interactionInterface: InteractionInterface?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnTitle.setOnClickListener {
            interactionInterface?.changeColor()//function calling that is declared inside interface
        }

    }

    fun changeText(){
        binding.btnTitle.setText("Changed by fragment")
    }
}