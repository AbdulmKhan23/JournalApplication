package com.khan.journalapplication.repository

import com.khan.journalapplication.data.DatabaseDAO
import com.khan.journalapplication.model.Journal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class JournalRepo @Inject constructor(private val journalDao: DatabaseDAO) {
    suspend fun addJournal(journal: Journal) = journalDao.insertJournal(journal)
    suspend fun updateJournal(journal: Journal) = journalDao.updateJournal(journal)
    suspend fun  deleteJournal(journal: Journal) = journalDao.deleteJournal(journal)
    suspend fun  deleteAllJournals() = journalDao.deleteAllJournals()
    fun getAllJournals(): Flow<List<Journal>> = journalDao.getAllJournals().flowOn(Dispatchers.IO)
}