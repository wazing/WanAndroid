package com.wazing.wanandroid.di

import android.content.Context
import com.wazing.wanandroid.model.room.WanRoomDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val singleModule = module {
    single {
        androidApplication().getSharedPreferences("app_shared_prefs", Context.MODE_PRIVATE)
    }
    single {
        WanRoomDatabase.getInstance(androidApplication()).userDao()
    }
    single {
        WanRoomDatabase.getInstance(androidApplication()).collectDao()
    }
}