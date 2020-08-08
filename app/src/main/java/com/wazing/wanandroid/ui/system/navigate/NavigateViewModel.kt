package com.wazing.wanandroid.ui.system.navigate

import com.wazing.common.base.BaseViewModel

class NavigateViewModel(private val repository: NavigateRepository) : BaseViewModel() {

    val navTree = call {
        repository.getTrees()
    }

}