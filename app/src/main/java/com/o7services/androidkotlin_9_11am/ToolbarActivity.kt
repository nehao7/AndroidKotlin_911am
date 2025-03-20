package com.o7services.androidkotlin_9_11am

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.o7services.androidkotlin_9_11am.databinding.ActivityToolbarBinding


class ToolbarActivity : AppCompatActivity() {
    lateinit var binding: ActivityToolbarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityToolbarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        binding.apply {

            toolbar.title = "My App"
            toolbar.subtitle = "Welcome"

            toolbar.setNavigationOnClickListener {
                Toast.makeText(this@ToolbarActivity, "Navigation Icon Clicked", Toast.LENGTH_SHORT).show()
            }

            fab.setOnClickListener{
                val intent = Intent(this@ToolbarActivity,NestedScrollActivity::class.java)
                startActivity(intent)

            }

            nestedScroll.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY > oldScrollY) {
                    Toast.makeText(this@ToolbarActivity, "Scrolled", Toast.LENGTH_SHORT).show()
                } else {
                }
            }
        }
    }

    override fun registerReceiver(
        receiver: BroadcastReceiver?,
        filter: IntentFilter?
    ): Intent? {
        return if (Build.VERSION.SDK_INT >= 34 && applicationInfo.targetSdkVersion >= 34) {
            super.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED)
        } else {
            super.registerReceiver(receiver, filter)
        }
    }
}