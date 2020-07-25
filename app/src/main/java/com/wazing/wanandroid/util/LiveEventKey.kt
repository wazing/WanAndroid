package com.wazing.wanandroid.util

import com.google.gson.Gson
import com.wazing.wanandroid.model.User

/* start --- App default key */
// 首页banner自动滑动延迟时间
const val BANNER_TIME_DEFAULT: Int = 2500
/* end --- Event key */

/* start --- Event key */
// 登录成功后用户信息
const val EVENT_USER_INFO: String = "user_info"

// 收藏id
const val EVENT_USER_COLLECT_IDS = "user_collect_ids"
/* end --- Event key */

/* start --- SharedPreferences */
// 本地持久化
const val SP_IS_LOGIN = "is_login"
const val SP_USER_ID = "user_id"
const val SP_USER_INFO = "user_info"
/* end --- SharedPreferences */

object Single {

    private val gson = Gson()

    fun toJson(user: User): String = gson.toJson(user)

    fun toUser(json: String?): User? =
        if (json == null) null else gson.fromJson<User>(json, User::class.java)

}

fun Int.isZero() = this == 0