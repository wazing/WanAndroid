package com.wazing.wanandroid.model.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AnyConverters {

    private val gson = Gson()

    @TypeConverter
    fun revert(value: String): List<Any> {
        val listType = object : TypeToken<List<Any>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun converter(list: List<Any>): String {
        return gson.toJson(list)
    }

}