package com.wazing.wanandroid.model.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.wazing.wanandroid.model.entity.Collect

@Dao
interface CollectDao {

    @Query("SELECT * FROM collect_table")
    fun getCollectList(): PagingSource<Int, Collect>

}