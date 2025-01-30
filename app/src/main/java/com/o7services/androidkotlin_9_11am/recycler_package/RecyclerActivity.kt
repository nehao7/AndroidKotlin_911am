package com.o7services.androidkotlin_9_11am.recycler_package

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.o7services.androidkotlin_9_11am.R
import com.o7services.androidkotlin_9_11am.databinding.ActivityRecyclerBinding
import com.o7services.androidkotlin_9_11am.databinding.FragmentNew2Binding
import com.o7services.androidkotlin_9_11am.list_package.Student

class RecyclerActivity : AppCompatActivity(), RecyclerAdapter.OnClick {
    lateinit var binding: ActivityRecyclerBinding
    lateinit var recyclerAdapter: RecyclerAdapter
    var list= arrayListOf<Student>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        list.add(Student(1,"A","Sub"))
        list.add(Student(1,"B","Sub"))
        list.add(Student(1,"C","Sub"))
        recyclerAdapter=RecyclerAdapter(list,this)
        binding.recycler.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.recycler.adapter=recyclerAdapter

    }

    override fun update(position: Int) {
        Toast.makeText(this,"Update Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun delete(position: Int) {
        Toast.makeText(this,"Delete Clicked", Toast.LENGTH_SHORT).show()

    }
}