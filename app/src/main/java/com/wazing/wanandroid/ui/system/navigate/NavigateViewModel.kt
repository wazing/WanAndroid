package com.wazing.wanandroid.ui.system.navigate

import com.wazing.wanandroid.base.BaseViewModel
import com.wazing.wanandroid.ui.system.navigate.NavigateRepository

class NavigateViewModel(private val repository: NavigateRepository) : BaseViewModel() {

    val navTree = call {
        repository.getTrees()
    }

}