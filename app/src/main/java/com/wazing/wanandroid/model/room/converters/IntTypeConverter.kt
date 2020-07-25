package com.wazing.wanandroid.model.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class IntTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun revert(value: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun converter(list: List<Int>): String {
        return gson.toJson(list)
    }

}