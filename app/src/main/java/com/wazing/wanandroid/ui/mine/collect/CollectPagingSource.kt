package com.wazing.wanandroid.ui.mine.collect

import androidx.paging.PagingSource
import com.wazing.wanandroid.model.api.checkResult
import com.wazing.wanandroid.model.entity.Collect

class CollectPagingSource(
    private val repository: CollectRepository
) : PagingSource<Int, Collect>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Collect> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val pagination = repository.getCollects(page).checkResult()
            LoadResult.Page(
                data = pagination.datas,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (pagination.over) null else pagination.curPage
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

}