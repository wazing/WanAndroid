package com.wazing.wanandroid.ui.system.system

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wazing.wanandroid.model.Article
import com.wazing.wanandroid.model.api.ApiService
import com.wazing.wanandroid.model.api.checkResult
import com.wazing.wanandroid.ui.system.pagingsource.SystemPagingSource
import kotlinx.coroutines.flow.Flow

class SystemRepository(private val apiService: ApiService) {

    private var cid: Int = 0

    fun setCid(cid: Int) {
        this.cid = cid
    }

    suspend fun getTrees() = apiService.getSystemTrees().checkResult()

    suspend fun getArticles(page: Int) = apiService.getSystemArticle(page, cid)

    fun getArticleList(): Flow<PagingData<Article>> {
        return Pager(config = PagingConfig(pageSize = 15)) {
            SystemPagingSource(this)
        }.flow
    }

}