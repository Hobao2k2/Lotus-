//package com.example.lotus.data.di
//
//import android.content.Context
//import com.chuckerteam.chucker.api.ChuckerInterceptor
//import com.example.lotus.data.network.ApiService
//import com.example.lotus.utils.SharedPrefManager
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//class NetworkModule {
//
//    @Singleton
//    @Provides
//    fun provideOkHttp(context: Context, sharedPrefManager: SharedPrefManager) :  OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                val token = sharedPrefManager.getToken() ?: ""
//                val request = chain.request()
//                val newRequest = request.newBuilder()
//                    .addHeader(
//                        "Authorization",
//                        token
//
//                    )
//                    .build()
//                chain.proceed(newRequest)
//            }
//            .addInterceptor(ChuckerInterceptor(context))
//            .build()
//    }
//
//    @Provides
//    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl("http://princehunganh.ddnsfree.com:7554")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideApiService(retrofit: Retrofit): ApiService {
//        return retrofit.create(ApiService::class.java)
//    }
//}