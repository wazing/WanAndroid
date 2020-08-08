package com.wazing.wanandroid.ui.mine.coin

import androidx.paging.PagingSource
import com.wazing.common.data.checkResult
import com.wazing.wanandroid.model.entity.CoinInfo

class CoinPagingSource(private val repository: CoinRepository) : PagingSource<Int, CoinInfo>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, CoinInfo> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val pagination = repository.getCoinList(page).checkResult()
            LoadResult.Page(
                data = pagination.datas,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (pagination.over) null else pagination.curPage + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

}