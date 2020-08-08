package com.wazing.wanandroid.plugin

object Versions {
    val compileSdk = 30
    val buildTools = "30.0.0"
    val minSdk = 26
    val targetSdk = 30
    val versionCode = 1
    val versionName = "1.0.0"

    val appcompat = "1.1.0"
    val kotlin = "1.3.72"
    val coreKtx = "1.3.1"
    val material = "1.2.0"
    val constraintlayout = "2.0.0-rc1"
    val swiperefreshlayout = "1.1.0"
    val lifecycle = "2.2.0"
    val room = "2.3.0-alpha01"
    val paging = "3.0.0-alpha01"
    val navigation = "2.3.0"
    val coroutines = "1.3.7"
    val koin = "2.1.6"
    val retrofit = "2.9.0"
    val converterGson = "2.9.0"
    val loggingInterceptor = "3.12.3"
    val coil = "0.11.0"

    val banner = "1.4.10"

    val leakcanary = "2.4"
}

object Libs {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"

    val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    val banner = "com.youth.banner:banner:${Versions.banner}"
}