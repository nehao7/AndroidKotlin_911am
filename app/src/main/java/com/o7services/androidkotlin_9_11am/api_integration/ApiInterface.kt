package com.shruti.apiintegrationapp

import com.o7services.androidkotlin_9_11am.api_integration.DeleteApiModel
import com.o7services.androidkotlin_9_11am.api_integration.QuotesDataClass
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiInterface {
    @Headers("x-rapidapi-key: bcf576e2ecmsh8d4af5b1d6f4110p1db621jsnb9ab811043b2",
        "x-rapidapi-host: quotes21.p.rapidapi.com")
    @GET("quote")
    fun listQuotes() : Call<QuotesDataClass>

    @DELETE("objects/{id}")
    suspend fun deleteObject(@Path("id") id: String): Response<DeleteApiModel>

}