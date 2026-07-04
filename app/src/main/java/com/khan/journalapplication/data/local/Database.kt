package com.khan.journalapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.khan.journalapplication.model.Journal

@Database(entities = [Journal::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class Database : RoomDatabase() {

    abstract fun journalDao(): DatabaseDAO

}