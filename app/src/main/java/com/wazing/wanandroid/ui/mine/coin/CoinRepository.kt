package com.wazing.wanandroid.ui.mine.coin

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.wazing.wanandroid.model.api.ApiService

class CoinRepository(private val api: ApiService) {

    suspend fun getCoinList(page: Int) = api.getCoinList(page)

    fun getCoinList() = Pager(config = PagingConfig(pageSize = 15)) {
        CoinPagingSource(this)
    }.flow

}