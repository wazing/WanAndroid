package com.wazing.wanandroid.ui.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wazing.common.data.checkResult
import com.wazing.wanandroid.model.Article
import com.wazing.wanandroid.model.api.ApiService
import kotlinx.coroutines.flow.Flow

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