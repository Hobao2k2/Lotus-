package com.example.lotus.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://princehunganh.ddnsfree.com:7554"

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val newRequest = request.newBuilder()
                    .addHeader(
                        "Authorization",
                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRW1haWwiOiJiYW9uYW1ra2FAZ21haWwuY29tIiwidXNlcklkIjoiNjZiMDc4ZmNiODIyOTllNGI5ZWI2MDM2IiwiaWF0IjoxNzIzMDAxODk0LCJleHAiOjE3MjMwMDU0OTR9.706iRmBNgS5TRoXo_-UQOczPBq7s_oGiUz8KrGfinGM"
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
