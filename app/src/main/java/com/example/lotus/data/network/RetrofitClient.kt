package com.example.lotus.data.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://princehunganh.ddnsfree.com:7554"
    var token: String = ""

    private fun provideOkHttpClient(context : Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val newRequest = request.newBuilder()
                    .addHeader(
                        "Authorization",
                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRW1haWwiOiJiYW9uYW1AZ21haWwuY29tIiwidXNlcklkIjoiNjZiNDllNDViODIyOTllNGI5ZWI2MzBhIiwiaWF0IjoxNzIzMTc3MzQ0LCJleHAiOjE3MjMxODA5NDR9.nMh4e2t8OZudcUwNxftMtw4XIVAUVhEKMQC-8MBontk"

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
