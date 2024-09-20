package com.example.lotus.data.network

import com.example.lotus.utils.ConvertPost
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.lotus.data.model.User
import com.example.lotus.utils.SharedPrefManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

    private fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(User::class.java, ConvertPost())  // Đăng ký UserDeserializer
            .create()
    }

    fun getRetrofitClient(context: Context): ApiService {
//        val gson = provideGson()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient(context))  // OkHttpClient với interceptor
            .addConverterFactory(GsonConverterFactory.create())  // Sử dụng Gson tùy chỉnh
            .build()
            .create(ApiService::class.java)
    }
    
}
