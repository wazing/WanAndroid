package com.wazing.wanandroid.model.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wazing.wanandroid.model.entity.Coin
import com.wazing.wanandroid.model.User
import com.wazing.wanandroid.model.entity.Collect
import com.wazing.wanandroid.model.room.dao.CollectDao
import com.wazing.wanandroid.model.room.dao.UserDao

@Database(
    entities = [
        User::class, Coin::class, Collect::class
    ], version = 1, exportSchema = false
)
abstract class WanRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun collectDao(): CollectDao

    companion object {
        private const val DB_NAME = "wan_db"

        @Volatile
        private var INSTANCE: WanRoomDatabase? = null

        fun getInstance(context: Application): WanRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val roomCallback = object : RoomDatabase.Callback() {

                }
                Room.databaseBuilder(context, WanRoomDatabase::class.java, DB_NAME)
                    .addCallback(roomCallback)
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }

}