package com.wazing.wanandroid.di

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.wazing.wanandroid.BuildConfig
import com.wazing.wanandroid.model.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

private const val API_URL = "https://www.wanandroid.com"
private const val TIME_OUT: Long = 10 * 1000

val networkModule = module {

    single {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(get<Context>()))
    }

    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }

    single {
        OkHttpClient.Builder()
            .callTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .cookieJar(get<PersistentCookieJar>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(API_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create<ApiService>()
    }

}