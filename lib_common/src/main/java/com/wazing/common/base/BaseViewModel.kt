package com.wazing.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.wazing.common.data.ApiException
import com.wazing.common.data.LoadStatus
import com.wazing.common.data.ResultCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    open val statusLiveData by lazy { MutableLiveData<LoadStatus>() }

    fun launch(
        block: suspend CoroutineScope.() -> Unit,
        error: ((errorMessage: String) -> Unit)? = null,
        complete: (() -> Unit)? = null
    ) = viewModelScope.launch {
        try {
            block.invoke(this)
        } catch (ex: Exception) {
            error?.invoke(ApiException.checkException(ex))
        } finally {
            complete?.invoke()
        }
    }

    fun <T : Any> call(
        context: CoroutineContext = Dispatchers.Main,
        block: suspend () -> T
    ) = liveData<ResultCallback<T>>(context) {
        try {
            emit(ResultCallback.Loading())
            emit(ResultCallback.Success(block.invoke()))
        } catch (ex: Exception) {
            val errorMsg = ApiException.checkException(ex)
            emit(ResultCallback.Error(errorMsg))
        }
        emit(ResultCallback.Complete())
    }

}