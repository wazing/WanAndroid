package com.wazing.wanandroid.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_table")
data class Coin(
    @PrimaryKey var userId: Int,
    val username: String,
    val coinCount: Int,
    val level: Int,
    val rank: Int
)