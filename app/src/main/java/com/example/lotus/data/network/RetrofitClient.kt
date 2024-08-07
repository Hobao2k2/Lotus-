package com.example.lotus.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

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
                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRW1haWwiOiJiYW9uYW1ra2FAZ21haWwuY29tIiwidXNlcklkIjoiNjZiMDc4ZmNiODIyOTllNGI5ZWI2MDM2IiwiaWF0IjoxNzIyOTE1OTkxLCJleHAiOjE3MjI5MTk1OTF9.Gzh_Z1DIt4P14gg3fmPTWelh1v_NsM4XqzybwGhK7GE"
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
class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build()
        return chain.proceed(request)
    }
}
object RetrofitInstance {
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRW1haWwiOiJidWltYW5kYWMxNDAyQGdtYWlsLmNvbSIsInVzZXJJZCI6IjY2YjE5N2M1YjgyMjk5ZTRiOWViNjA5NiIsImlhdCI6MTcyMjk5Njc1NiwiZXhwIjoxNzIzMDAwMzU2fQ.bP4KEN6ervThJKaSbc4rJIVrB0-036PcUpueBowD7eU")) // Thay "your_token_here" bằng token thực tế
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://princehunganh.ddnsfree.com:7554")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
