package com.wazing.wanandroid.ui.mine.coin

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.wazing.wanandroid.base.BaseViewModel

class CoinViewModel(private val repository: CoinRepository) : BaseViewModel() {

    val coinList = repository.getCoinList().cachedIn(viewModelScope)

}