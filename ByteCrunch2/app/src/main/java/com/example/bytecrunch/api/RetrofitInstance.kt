package com.example.bytecrunch.api

import com.example.bytecrunch.helper.Constants.Companion.NEWSAPI_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This class creates singleton retrofit instance
 * For making API request
 */
class RetrofitInstance {


    companion object {

        // Initialize retrofit only once
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()

            Retrofit.Builder()
                    .baseUrl(NEWSAPI_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
        }


        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }
    }
}