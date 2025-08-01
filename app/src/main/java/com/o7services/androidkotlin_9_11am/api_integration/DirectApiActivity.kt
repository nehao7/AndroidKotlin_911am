package com.o7services.androidkotlin_9_11am.api_integration

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.o7services.androidkotlin_9_11am.R
import com.o7services.androidkotlin_9_11am.databinding.ActivityDirectApiBinding
import com.shruti.apiintegrationapp.RetrofitClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        deleteItemById(id = "ff8081819782e69e0198646963eb533c")
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

    fun deleteItemById(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
            var response = RetrofitClass.delinstance.deleteObject(id)
            var result = response.body()
            if (response.isSuccessful) {
                val res = response.body()?.toString()
                val resbody = Gson().fromJson(res, DeleteApiModel::class.java)
                Log.d("Delete Api", "Success:${resbody.message} ")
//                Log.d("Delete Api","${result?.message}")
                withContext(Dispatchers.Main){
                    Toast.makeText(this@DirectApiActivity, "${resbody?.message}", Toast.LENGTH_SHORT).show()
                }
            }else{

                val errorBody = response.errorBody()?.string()
                val errorbody = Gson().fromJson(errorBody, ErrorModel::class.java)
                Log.d("Delete Api", "Failed:${errorbody.error} ")
                withContext(Dispatchers.Main){
                    Toast.makeText(this@DirectApiActivity, "${errorbody?.error}", Toast.LENGTH_SHORT).show()

                }

            }
            }catch (e:Exception){
                Log.d("Delete Api", "Error:${e.message}")
                withContext(Dispatchers.Main){

                }
            }
        }
    }
}