package com.wazing.wanandroid.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wazing.wanandroid.model.Article
import com.wazing.wanandroid.model.api.ApiService
import com.wazing.wanandroid.model.api.checkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import java.lang.AssertionError
import java.lang.RuntimeException
import java.util.concurrent.TimeoutException

class HomeRepository(private val api: ApiService) {

    /**
     * 轮播列表
     */
    suspend fun getBannerList() = api.getBanners().checkResult()

    /**
     * 置顶文章
     */
    suspend fun getTopArticles() = api.getTopArticles()

    /**
     * 根据 page 获取文章
     */
    suspend fun getArticles(page: Int) = api.getArticles(page)

    /**
     * paging3 分页加载数据
     */
    fun getArticleList(): Flow<PagingData<Article>> {
        return Pager(config = PagingConfig(pageSize = 15)) {
            HomePagingSource(this)
        }.flow
    }

}