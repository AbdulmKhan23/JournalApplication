package com.khan.journalapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.khan.journalapplication.model.Journal
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDAO {
    @Query("SELECT * FROM journal_table")
    fun getAllJournals(): Flow<List<Journal>>

    @Query("SELECT * FROM journal_table WHERE id = :id")
    suspend fun getJournalById(id: String): Journal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(journal: Journal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateJournal(journal: Journal)

    @Query("DELETE from journal_table")
    suspend fun deleteAllJournals()

    @Delete
    suspend fun deleteJournal(journal: Journal)
}


