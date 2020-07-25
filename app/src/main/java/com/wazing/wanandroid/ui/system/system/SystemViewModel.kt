package com.wazing.wanandroid.ui.system.system

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.wazing.wanandroid.base.BaseViewModel

class SystemViewModel(private val repository: SystemRepository) : BaseViewModel() {

    fun setCid(cid: Int) {
        repository.setCid(cid)
    }

    val treeList = call {
        repository.getTrees()
    }

    val articles = repository.getArticleList().cachedIn(viewModelScope)

}