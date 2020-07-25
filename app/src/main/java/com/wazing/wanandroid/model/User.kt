package com.wazing.wanandroid.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.wazing.wanandroid.model.room.converters.AnyConverters
import com.wazing.wanandroid.model.room.converters.IntTypeConverter

@Entity(tableName = "user_table")
@TypeConverters(IntTypeConverter::class, AnyConverters::class)
data class User constructor(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val username: String,
    val nickname: String,
    val publicName: String,
    val password: String,
    val email: String,
    val icon: String,
    val token: String,
    val type: Int,
    val admin: Boolean,
    val collectIds: List<Int>,
    val chapterTops: List<Any>
)