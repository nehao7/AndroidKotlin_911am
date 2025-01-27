package com.o7services.androidkotlin_9_11am.list_package

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.o7services.androidkotlin_9_11am.R
import com.o7services.androidkotlin_9_11am.databinding.ActivityListBaseAdapterBinding

class ListBaseAdapterActivity : AppCompatActivity() {
    lateinit var binding: ActivityListBaseAdapterBinding
    var list = arrayListOf("c", "C++", "Java")
    var studentList = arrayListOf<Student>()
    var listAdapter = ListAdapter(studentList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListBaseAdapterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        studentList.add(Student(name="Test", subject = "C"))
        studentList.add(Student(rollNo = 2, "Bjarne", "C++"))
        studentList.add(Student(rollNo = 3, "James", "Java"))
        binding.listiewnew.adapter=listAdapter
    }
}