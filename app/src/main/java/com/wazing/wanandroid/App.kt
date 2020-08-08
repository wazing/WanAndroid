package com.wazing.wanandroid

import com.wazing.common.base.BaseApplication
import com.wazing.wanandroid.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(koinModules)
        }
    }
}