package com.example.lotus.data.network

import android.content.Context
import com.example.lotus.ui.view.LoginFragment
import com.example.lotus.utils.SharedPrefManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(private val context: Context) {
    private val BASE_URL = "http://princehunganh.ddnsfree.com:7554"

    private lateinit var sharedPrefManager: SharedPrefManager

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
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
