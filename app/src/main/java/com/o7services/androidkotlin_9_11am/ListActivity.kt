package com.o7services.androidkotlin_9_11am

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.o7services.androidkotlin_9_11am.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {
    lateinit var binding: ActivityListBinding
    var array = arrayListOf("First", "Second", "Third")
    var arrayAdapter : ArrayAdapter<String>?= null
    //    lateinit var arrayAdapter: ArrayAdapter<String>
    private val TAG = "ListActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        array.add("four")
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
        binding.listView .adapter= arrayAdapter
        binding.listView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this@ListActivity,"$position", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "${parent.getItemAtPosition(position)}")
        }
        binding.listView.setOnItemLongClickListener { parent, view, position, id ->
            Toast.makeText(this@ListActivity, "", Toast.LENGTH_SHORT).show()
            return@setOnItemLongClickListener true
        }
        arrayAdapter?.notifyDataSetChanged()
    }
}