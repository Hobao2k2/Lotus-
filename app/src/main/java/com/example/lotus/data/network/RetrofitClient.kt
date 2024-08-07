package com.example.lotus.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://princehunganh.ddnsfree.com:7554"

    var token: String = ""

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val newRequest = request.newBuilder()
                    .addHeader(
                        "Authorization",
                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRW1haWwiOiJiYW9uYW1ra2FAZ21haWwuY29tIiwidXNlcklkIjoiNjZiMDc4ZmNiODIyOTllNGI5ZWI2MDM2IiwiaWF0IjoxNzIzMDE1MTA3LCJleHAiOjE3MjMwMTg3MDd9.qA1jm3KuZcfXnG6QafpM4GRLbFby34AU2mUpUTwarzU"

                    )
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    fun getRetrofitClient(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient()) // Cung cấp OkHttpClient với interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
