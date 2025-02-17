package com.o7services.androidkotlin_9_11am.firebase

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.o7services.androidkotlin_9_11am.R
import com.o7services.androidkotlin_9_11am.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var mainmenu=Unit
    private val TAG = "LoginActivity"
    var admintype = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.tvLogin.setOnClickListener {
//            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnsave.setOnClickListener {
             if (binding.edtemail.text.toString().isNullOrEmpty()) {
                binding.tilemail.isErrorEnabled = true
                binding.tilemail.error = "Enter Email"
            } else if (binding.edtPassword.text.toString().isNullOrEmpty()) {
                binding.tilPassword.isErrorEnabled = true
                binding.tilPassword.error = "Enter Password"
            } else {
                auth.createUserWithEmailAndPassword(
                    binding.edtemail.text.toString(),
                    binding.edtPassword.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Registration successful
                            val user = auth.currentUser

//                            startActivity(Intent(this, MainActivity::class.java))
                            Toast.makeText(this, "Registration Successful${user?.email}", Toast.LENGTH_SHORT)
                                .show()
//                            finish()
                            // Save user details to Firestore database

                        } else {
                            // Registration failed
                            // Handle error appropriately
                        }
                    }
            }
        }

    }
}