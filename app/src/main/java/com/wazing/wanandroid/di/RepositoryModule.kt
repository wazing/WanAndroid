package com.wazing.wanandroid.di

import com.wazing.wanandroid.model.api.ApiService
import com.wazing.wanandroid.ui.home.HomeRepository
import com.wazing.wanandroid.ui.mine.UserRepository
import com.wazing.wanandroid.ui.mine.coin.CoinRepository
import com.wazing.wanandroid.ui.mine.collect.CollectRepository
import com.wazing.wanandroid.ui.mine.login.RegisterRepository
import com.wazing.wanandroid.ui.system.navigate.NavigateRepository
import com.wazing.wanandroid.ui.system.system.SystemRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory { HomeRepository(get()) }

    single { UserRepository(get(), get()) }

    factory { CoinRepository(get<ApiService>()) }

    factory { RegisterRepository(get(), get()) }

    factory { CollectRepository(get(), get()) }

    factory { SystemRepository(get()) }
    factory { NavigateRepository(get()) }
}