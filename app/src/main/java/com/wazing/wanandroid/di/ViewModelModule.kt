package com.wazing.wanandroid.di

import com.wazing.wanandroid.ui.home.HomeViewModel
import com.wazing.wanandroid.ui.mine.UserViewModel
import com.wazing.wanandroid.ui.mine.coin.CoinRepository
import com.wazing.wanandroid.ui.mine.coin.CoinViewModel
import com.wazing.wanandroid.ui.mine.collect.CollectViewModel
import com.wazing.wanandroid.ui.mine.login.RegisterViewModel
import com.wazing.wanandroid.ui.system.navigate.NavigateViewModel
import com.wazing.wanandroid.ui.system.system.SystemViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { HomeViewModel(get()) }

    viewModel { UserViewModel(get()) }

    viewModel { CoinViewModel(get<CoinRepository>()) }

    viewModel { RegisterViewModel(get()) }

    viewModel { CollectViewModel(get()) }

    viewModel { SystemViewModel(get()) }

    viewModel { NavigateViewModel(get()) }
}