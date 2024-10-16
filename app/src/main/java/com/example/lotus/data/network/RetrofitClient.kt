package com.example.lotus.data.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.lotus.utils.SharedPrefManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val BASE_URL = "http://princehunganh.ddnsfree.com:7554"


    private fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val sharedPrefManager = SharedPrefManager(context)
                val token = sharedPrefManager.getToken() ?: ""
                val request = chain.request()
                val newRequest = request.newBuilder()
                    .addHeader(
                        "Authorization",
                        token
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
