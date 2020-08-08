package com.wazing.common.data

import android.accounts.NetworkErrorException
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

data class ApiException(val code: Int, override var message: String) : Exception() {

    companion object {
        fun checkException(exception: java.lang.Exception): String {
            return when (exception) {
                is SocketTimeoutException -> "连接超时，请检查网络"
                is NetworkErrorException -> "网络连接错误，请检查网络"
                is ConnectException -> "无网络连接，请检查网络"
                is MalformedJsonException, is JsonSyntaxException -> "解析Json异常"
                is UnknownHostException -> "可能网络未连接，请检查网络"
                is ApiException -> exception.message
                else -> exception.message ?: "未知错误"
            }
        }
    }
}

fun <T> DataResponse<T>.checkResult(): T {
    return if (code == 0) data else throw com.wazing.common.data.ApiException(
        code,
        msg
    )
}