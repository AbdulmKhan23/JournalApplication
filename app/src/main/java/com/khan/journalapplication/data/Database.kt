package com.khan.journalapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.khan.journalapplication.model.Journal
import com.khan.journalapplication.util.DateConverter
import com.khan.journalapplication.util.UUIDConverter

@Database(entities = [Journal::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun journalDao(): DatabaseDAO

}