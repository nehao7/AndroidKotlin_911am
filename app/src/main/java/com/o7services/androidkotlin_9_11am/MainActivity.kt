package com.o7services.androidkotlin_9_11am

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.o7services.androidkotlin_9_11am.databinding.ActivityMainBinding
import com.o7services.androidkotlin_9_11am.fragment_activity_interaction.BaseActivity
import com.o7services.androidkotlin_9_11am.list_package.ListBaseAdapterActivity
import com.o7services.androidkotlin_9_11am.recycler_package.RecyclerActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var btn: Button? = null
    var btnRelative: Button? = null
    var input: EditText? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "OnCreate", Toast.LENGTH_SHORT).show()
        btn = findViewById(R.id.btnWelcome)
        btnRelative = findViewById(R.id.RelativeLayout)
        input = findViewById(R.id.edtInput)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btn?.setOnClickListener {
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
        btnRelative?.setOnClickListener {
            var intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnImplicit).setOnClickListener {
            var text=input?.text.toString()
            var intent = Intent(this, ImplicitIntentActivity::class.java)
            intent.putExtra("data", input?.text.toString())
            intent.putExtra("data",text)
            startActivity(intent)
        }
        binding.btnAlertDialog.setOnClickListener {
            startActivity(Intent(this,AlertDialogActivity::class.java))
        }
        binding.btnSpinnerActivity.setOnClickListener {
            startActivity(Intent(this,SpinnerActivity::class.java))
        }
        binding.btnListViewActivity.setOnClickListener {
            startActivity(Intent(this,ListActivity::class.java))
        }
        binding.btnBaseAdapterActivity.setOnClickListener {
            startActivity(Intent(this,ListBaseAdapterActivity::class.java))
        }
        binding.btnBaseActivity.setOnClickListener {
            startActivity(Intent(this,BaseActivity::class.java))
        }
        binding.btnRecyclerActivity.setOnClickListener {
            startActivity(Intent(this,RecyclerActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "OnStart", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "OnResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "OnPause", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "OnDestroy", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "OnRestart", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "OnStop", Toast.LENGTH_SHORT).show()
    }

}