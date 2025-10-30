package com.example.registrojugadores.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromLogrosList(list: List<String>?): String {
        return gson.toJson(list ?: emptyList<String>())
    }

    @TypeConverter
    fun toLogrosList(value: String?): List<String> {
        if (value.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromBoardList(board: List<Int?>?): String {
        return gson.toJson(board ?: emptyList<Int?>())
    }

    @TypeConverter
    fun toBoardList(boardString: String?): List<Int?> {
        if (boardString.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<Int?>>() {}.type
        return gson.fromJson(boardString, type)
    }
}




