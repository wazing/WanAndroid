package com.wazing.common.data

sealed class ResultCallback<Value : Any> {

    data class Loading<Value : Any>(val message: String = "loading") : ResultCallback<Value>()

    data class Success<Value : Any>(val data: Value) : ResultCallback<Value>()

    data class Error<Value : Any>(val message: String) : ResultCallback<Value>()

    data class Complete<Value : Any>(val message: String = "complete") : ResultCallback<Value>()
}