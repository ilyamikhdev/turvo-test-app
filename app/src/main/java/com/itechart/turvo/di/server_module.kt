package ru.mealty.di

import android.util.Log
import com.itechart.turvo.BuildConfig
import com.itechart.turvo.network.ApiServer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val serverModule = module {
    single { createOkHttpClient() }
    single { createWebService<ApiServer>(get(), BuildConfig.API_URL) }
}

fun createOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()

    builder.addNetworkInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.d("ApiServer", message)
        }
    }).apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    })

    return builder.build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T =
    Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(T::class.java)