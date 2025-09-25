package com.example.registrojugadores.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun toBoardList(boardString: String?): List<Int?>? {
        if (boardString == null) return emptyList()
        return try {
            val type = object : TypeToken<List<Int?>>() {}.type
            Gson().fromJson(boardString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    @TypeConverter
    fun fromBoardList(board: List<Int?>?): String? {
        return Gson().toJson(board)
    }
}