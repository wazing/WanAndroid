package com.wazing.wanandroid.ui.system.pagingsource

import androidx.paging.PagingSource
import com.wazing.wanandroid.model.Article
import com.wazing.wanandroid.model.api.checkResult
import com.wazing.wanandroid.ui.system.system.SystemRepository

class SystemPagingSource(private val repository: SystemRepository) : PagingSource<Int, Article>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val pagination = repository.getArticles(page).checkResult()
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