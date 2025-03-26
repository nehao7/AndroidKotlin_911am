package com.o7services.androidkotlin_9_11am

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.o7services.androidkotlin_9_11am.DrawerLayout.NavDrawerActivity
import com.o7services.androidkotlin_9_11am.api_integration.DirectApiActivity
import com.o7services.androidkotlin_9_11am.bottomnavigation.BottomNavigationActivity
import com.o7services.androidkotlin_9_11am.databinding.ActivityMainBinding
import com.o7services.androidkotlin_9_11am.firebase.FireBaseActivity
import com.o7services.androidkotlin_9_11am.firebase.FirestoreActivity
import com.o7services.androidkotlin_9_11am.firebase.RegisterActivity
import com.o7services.androidkotlin_9_11am.fragment_activity_interaction.BaseActivity
import com.o7services.androidkotlin_9_11am.jetpacknav.NavControllerActivity
import com.o7services.androidkotlin_9_11am.list_package.ListBaseAdapterActivity
import com.o7services.androidkotlin_9_11am.realtimedatabase.RealtimeActivity
import com.o7services.androidkotlin_9_11am.recycler_package.RecyclerActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var btn: Button? = null
    var btnRelative: Button? = null
    var input: EditText? = null
    private val TAG = "FCM Logs"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Toast.makeText(this, "OnCreate", Toast.LENGTH_SHORT).show()
        btn = findViewById(R.id.btnWelcome)
        btnRelative = findViewById(R.id.RelativeLayout)
        input = findViewById(R.id.edtInput)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Request permission for API 33+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {

                requestPermission()
            }
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            Log.d(TAG, token)

        })


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
        binding.btnNavControllerActivity.setOnClickListener {
            startActivity(Intent(this,NavControllerActivity::class.java))
        }
        binding.btnBottomNav.setOnClickListener {
            startActivity(Intent(this,BottomNavigationActivity::class.java))
        }
        binding.btnfirebase.setOnClickListener {
            startActivity(Intent(this,FireBaseActivity::class.java))
        }
        binding.btnWebViewActivity.setOnClickListener {
            startActivity(Intent(this,WebViewActivity::class.java))
        }

        binding.btnDrawerLayout.setOnClickListener {
            startActivity(Intent(this,NavDrawerActivity::class.java))
        }
        binding.btnRealTimeDatabase.setOnClickListener {
            startActivity(Intent(this,RealtimeActivity::class.java))
        }
        binding.btnLocationActivity.setOnClickListener {
            startActivity(Intent(this,LocationActivity::class.java))
        }
        binding.btnFirestore.setOnClickListener {
            startActivity(Intent(this,FirestoreActivity::class.java))
        }
        binding.btnSharedPref.setOnClickListener {
            startActivity(Intent(this,SharedPrefActivity::class.java))
        }
        binding.btnToolbar.setOnClickListener {
            startActivity(Intent(this,ToolbarActivity::class.java))
        }
        binding.btnApi.setOnClickListener {
            startActivity(Intent(this,DirectApiActivity::class.java))
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
        }
    }
    override fun onStart() {
        super.onStart()
//        Toast.makeText(this, "OnStart", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
//        Toast.makeText(this, "OnResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
//        Toast.makeText(this, "OnPause", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
//        Toast.makeText(this, "OnDestroy", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
//        Toast.makeText(this, "OnRestart", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
//        Toast.makeText(this, "OnStop", Toast.LENGTH_SHORT).show()
    }

}