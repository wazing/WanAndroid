package com.wazing.wanandroid.ui.system.navigate

import com.wazing.common.data.checkResult
import com.wazing.wanandroid.model.api.ApiService

class NavigateRepository(private val apiService: ApiService) {

    suspend fun getTrees() = apiService.getNavigateTrees().checkResult()

}