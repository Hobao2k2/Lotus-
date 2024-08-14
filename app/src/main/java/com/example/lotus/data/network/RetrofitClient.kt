package com.example.lotus.data.network

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.lotus.utils.SharedPrefManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://princehunganh.ddnsfree.com:7554"

    private fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val token = SharedPrefManager(context).getToken() ?: ""
                Log.d("TAG", "provideOkHttpClient: $token")
                val newRequest = request.newBuilder()
                    .addHeader(
                        "Authorization",
           //             "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRW1haWwiOiJiYW9uYW1AZ21haWwuY29tIiwidXNlcklkIjoiNjZiNDllNDViODIyOTllNGI5ZWI2MzBhIiwiaWF0IjoxNzIzNjAyNzQxLCJleHAiOjE3MjYxOTQ3NDF9.ltLQH86UMpO1MNROEivkPYjo8LVDTyRGnmxMhhwlZIw"
"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRW1haWwiOiJiYW9uYW1ra2FAZ21haWwuY29tIiwidXNlcklkIjoiNjZiMDc4ZmNiODIyOTllNGI5ZWI2MDM2IiwiaWF0IjoxNzIzNjA4MTAxLCJleHAiOjE3MjYyMDAxMDF9.MlQMH262ETRYRhHnm6m6Od5NmuwFYf6x7nUgWNTUBWo"
                    )
                    .build()
                chain.proceed(newRequest)
            }
            .addInterceptor(ChuckerInterceptor(context))
            .build()
    }

    fun getRetrofitClient(context: Context): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient(context)) // Cung cấp OkHttpClient với interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
