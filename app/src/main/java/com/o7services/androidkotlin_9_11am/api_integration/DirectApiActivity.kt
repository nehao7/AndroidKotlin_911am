package com.o7services.androidkotlin_9_11am.api_integration

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.o7services.androidkotlin_9_11am.R
import com.o7services.androidkotlin_9_11am.databinding.ActivityDirectApiBinding
import com.shruti.apiintegrationapp.RetrofitClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DirectApiActivity : AppCompatActivity() {
    lateinit var binding:ActivityDirectApiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityDirectApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fetchQuotes()
    }

    fun fetchQuotes(){
        RetrofitClass.instance.listQuotes().enqueue(object : Callback<QuotesDataClass> {
            override fun onResponse(
                call: Call<QuotesDataClass>,
                response: Response<QuotesDataClass>
            ) {
                if (response.isSuccessful){
                    val list = response.body()
                    Log.d("Response","Response Body ${list}" )
                    if (list != null){
                        binding.tvName.setText(list.quote)
                        binding.tvAuthor.setText(list.author)
                    }
                    else{
                        Log.e("Api error ", "Response failed: ${response.errorBody().toString()}")
                    }
                }
                else {
                    Log.e("API Error", "Response failed: ${response.errorBody().toString()}")
                }
            }

            override fun onFailure(call: Call<QuotesDataClass>, t: Throwable) {
                Log.e("API Error ", "Request failed : ${t.message}")
            }
        })
    }
}