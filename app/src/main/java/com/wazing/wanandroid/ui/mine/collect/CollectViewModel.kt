package com.wazing.wanandroid.ui.mine.collect

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.wazing.common.base.BaseViewModel

class CollectViewModel(private val repository: CollectRepository) : BaseViewModel() {

    /**
     * 收藏列表
     */
    val collects = repository.getCollectList().cachedIn(viewModelScope)

    /**
     * 根据id收藏or取消收藏
     */
    fun collectArticleById(id: Int, collectStatus: Boolean) {
        launch(
            block = {
                if (collectStatus) {
                    repository.unCollect(id)
                } else {
                    repository.collect(id)
                }
            }
        )
    }

}