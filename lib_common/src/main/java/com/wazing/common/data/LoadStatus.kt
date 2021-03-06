package com.wazing.common.data

sealed class LoadStatus(endOfLoading: Boolean) {

    object Loading : LoadStatus(false)

    class Error(
        val error: Throwable? = null, val message: String? = error?.message
    ) : LoadStatus(true)

    object Complete : LoadStatus(true)

}