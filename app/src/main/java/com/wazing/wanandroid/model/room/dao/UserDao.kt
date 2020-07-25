package com.wazing.wanandroid.model.room.dao

import androidx.room.*
import com.wazing.wanandroid.model.entity.Coin
import com.wazing.wanandroid.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coin: Coin)

    /**
     * 根据userId读取用户信息
     * 返回值使用 Flow 便于观察数据变化后回调结果，使用Flow就不能挂起
     * 这里有个问题，返回值为 UserModel，viewmodel中使用 flow{} 拓展 emit 接收一次事件后就不会在接收了
     */
    @Transaction
    @Query("SELECT * FROM user_table WHERE id = :userId")
    fun getUserInfo(userId: Int?): Flow<User?>

    @Transaction
    @Query("SELECT * FROM coin_table WHERE userId = :userId")
    fun getCoin(userId: Int?): Flow<Coin?>

    @Transaction
    @Query("SELECT * FROM user_table")
    suspend fun getUserList(): List<User>

    @Transaction
    @Query("SELECT * FROM coin_table")
    suspend fun getCoinList(): List<Coin>

}