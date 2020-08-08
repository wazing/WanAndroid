package com.wazing.wanandroid.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.wazing.common.base.BaseViewModel
import com.wazing.wanandroid.model.BannerEntity
import com.wazing.common.data.ResultCallback

class HomeViewModel(
    private val repository: HomeRepository
) : BaseViewModel() {

    private val _banners = MutableLiveData<ResultCallback<List<BannerEntity>>>().apply {
        getBannerList()
    }

    val banners = liveData {
        emitSource(_banners)
    }

    val articles = repository.getArticleList().cachedIn(viewModelScope)

    fun getBannerList() {
        launch(
            block = {
                if (_banners.value != null && _banners.value is ResultCallback.Success) return@launch

                val result = repository.getBannerList()
                _banners.value = ResultCallback.Success(result)
            },
            error = {
                _banners.value = ResultCallback.Error(it)
            }
        )
    }

}