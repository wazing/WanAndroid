package com.wazing.wanandroid.ui.home

import androidx.paging.PagingSource
import com.wazing.common.data.checkResult
import com.wazing.wanandroid.model.Article

class HomePagingSource(
    private val repository: HomeRepository
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: STARTING_PAGE_INDEX
        // val pageSize = params.loadSize
//        println("===> ${Thread.currentThread().name}")
//        println("===> load: $page")
        return try {
            val topArticles = if (page == STARTING_PAGE_INDEX) {
                repository.getTopArticles().checkResult().map {
                    it.top = true
                    return@map it
                }
            } else emptyList()
            val pagination = repository.getArticles(page).checkResult()
            val list = mutableListOf<Article>().apply {
                addAll(topArticles)
                addAll(pagination.datas)
            }
            LoadResult.Page(
                data = list,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (pagination.over) null else pagination.curPage
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }

}