package com.wazing.wanandroid.ui.mine.login

import androidx.lifecycle.MutableLiveData
import com.wazing.common.base.BaseViewModel
import com.wazing.wanandroid.model.User
import com.wazing.common.data.ResultCallback

class RegisterViewModel(
    private val repository: RegisterRepository
) : BaseViewModel() {

    val user = MutableLiveData<ResultCallback<User>>()

    fun signIn(username: String, password: String) {
        register(true, username, password)
    }

    fun signUp(username: String, password: String) {
        register(false, username, password)
    }

    private fun register(isLogin: Boolean, username: String, password: String) {
        launch(
            block = {
                user.value = ResultCallback.Loading()
                val result = if (isLogin) {
                    repository.signIn(username, password)
                } else {
                    repository.signUp(username, password)
                }
                user.value = ResultCallback.Success(result)
            },
            error = {
                user.value = ResultCallback.Error(it)
            }
        )
    }

}