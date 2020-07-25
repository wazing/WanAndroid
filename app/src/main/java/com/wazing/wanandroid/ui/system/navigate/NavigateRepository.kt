package com.wazing.wanandroid.ui.system.navigate

import com.wazing.wanandroid.model.api.ApiService
import com.wazing.wanandroid.model.api.checkResult

class NavigateRepository(private val apiService: ApiService) {

    suspend fun getTrees() = apiService.getNavigateTrees().checkResult()

}