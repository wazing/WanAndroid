package com.wazing.wanandroid.ui.mine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.wazing.wanandroid.base.BaseViewModel
import com.wazing.wanandroid.model.entity.Coin

class UserViewModel(private val repository: UserRepository) : BaseViewModel() {

    private val _userId by lazy { MutableLiveData<Int>() }

    val isLogin = Transformations.map(_userId) {
        return@map it != null
    }

    fun setUserId(userId: Int?) {
        userId?.let {
            _userId.value = it
        }
    }

    fun getCoin() {
        launch(
            block = {
                repository.getCoin()
            }
        )
    }

    suspend fun user() = repository.getUser(_userId.value)

    suspend fun coin() = repository.getCoin(_userId.value)

}