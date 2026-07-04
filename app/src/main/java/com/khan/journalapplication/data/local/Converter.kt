package com.khan.journalapplication.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date
import java.util.UUID

class Converter {
    @TypeConverter
    fun timeStampFromDate(date: Date):Long{
        return date.time
    }
    @TypeConverter
    fun dateFromTimeStamp(timestamp:Long): Date{
        return Date(timestamp)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID):String?{
        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(string: String?):UUID?{
        return UUID.fromString(string)
    }
    @TypeConverter
    fun fromSuggestions(suggestions: List<String>): String {
        return Gson().toJson(suggestions)
    }

    @TypeConverter
    fun toSuggestions(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }

}