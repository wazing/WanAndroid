package com.wazing.wanandroid.ui.mine.login

import com.wazing.common.data.checkResult
import com.wazing.wanandroid.model.User
import com.wazing.wanandroid.model.api.ApiService
import com.wazing.wanandroid.model.room.dao.UserDao

class RegisterRepository(private val api: ApiService, private val userDao: UserDao) {

    suspend fun signIn(username: String, password: String): User {
        val result = api.signIn(username, password).checkResult()
        userDao.insert(result)
        return result
    }

    suspend fun signUp(username: String, password: String): User {
        val result = api.signUp(username, password).checkResult()
        userDao.insert(result)
        return result
    }

}