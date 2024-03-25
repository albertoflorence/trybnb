package com.betrybe.trybnb.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private var retrofit: Retrofit? = null
    fun getInstance(): ApiService {
        if (retrofit == null) {

            val client = OkHttpClient.Builder().build()
            retrofit =
                Retrofit.Builder().baseUrl("https://restful-booker.herokuapp.com/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create()).build()
        }

        return retrofit?.create(ApiService::class.java)!!
    }
}