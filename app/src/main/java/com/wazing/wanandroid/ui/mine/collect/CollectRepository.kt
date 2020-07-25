package com.wazing.wanandroid.ui.mine.collect

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wazing.wanandroid.model.api.ApiService
import com.wazing.wanandroid.model.entity.Collect
import com.wazing.wanandroid.model.room.dao.CollectDao
import kotlinx.coroutines.flow.Flow

class CollectRepository(private val api: ApiService, private val dao: CollectDao) {

    suspend fun getCollects(page: Int) = api.getCollects(page)

    suspend fun collect(id: Int) = api.collect(id)

    suspend fun unCollect(id: Int) = api.unCollect(id)

    fun getCollectList(): Flow<PagingData<Collect>> {
        val pagingConfig = PagingConfig(pageSize = 15)
        return Pager(pagingConfig) {
            CollectPagingSource(this)
        }.flow
    }

}