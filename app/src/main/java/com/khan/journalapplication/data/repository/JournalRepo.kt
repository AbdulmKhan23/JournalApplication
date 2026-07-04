package com.khan.journalapplication.data.repository

import com.khan.journalapplication.data.local.DatabaseDAO
import com.khan.journalapplication.data.remote.JournalApi
import com.khan.journalapplication.data.remote.JournalRequest
import com.khan.journalapplication.data.remote.JournalResponse
import com.khan.journalapplication.model.Journal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class JournalRepo @Inject constructor(
    private val journalDao: DatabaseDAO,
    private val journalApi: JournalApi
)
{
    // ROOM
    suspend fun addJournal(journal: Journal) =
        journalDao.insertJournal(journal)
    suspend fun updateJournal(journal: Journal) =
        journalDao.updateJournal(journal)
    suspend fun  deleteJournal(journal: Journal) =
        journalDao.deleteJournal(journal)
    suspend fun  deleteAllJournals() =
        journalDao.deleteAllJournals()
    fun getAllJournals() =
        journalDao.getAllJournals()
    suspend fun getJournalById(id: String): Journal?{
        return journalDao.getJournalById(id)
    }


    //FASTAPI
    suspend fun analyzeJournal(entry: String): JournalResponse {
        return journalApi.analyzeJournal(
            JournalRequest(entry)
        )
    }
}