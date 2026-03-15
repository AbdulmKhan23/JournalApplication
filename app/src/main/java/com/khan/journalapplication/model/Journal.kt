package com.khan.journalapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID
import java.time.Instant


@Entity(tableName = "journal_table")
data class Journal(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "journal_title")
    val title: String,

    @ColumnInfo(name = "journal_content")
    val content: String,

    @ColumnInfo(name = "journal_mood")
    val mood: String,

    @ColumnInfo(name = "journal_date")
    val entryDate: Date= Date.from(Instant.now())
)
