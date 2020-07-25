package com.wazing.wanandroid.ui.mine

import com.wazing.wanandroid.model.api.ApiService
import com.wazing.wanandroid.model.api.checkResult
import com.wazing.wanandroid.model.room.dao.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val api: ApiService, private val userDao: UserDao) {

    suspend fun getCoin() {
        val coin = api.getCoin().checkResult()
        userDao.insert(coin)
    }

    suspend fun getCoin(userId: Int?) = withContext(Dispatchers.IO) {
        userDao.getCoin(userId)
    }

    suspend fun getUser(userId: Int?) = withContext(Dispatchers.IO){
        userDao.getUserInfo(userId)
    }

}