package com.khan.journalapplication.data.repository

import com.khan.journalapplication.data.local.DatabaseDAO
import com.khan.journalapplication.data.remote.JournalApi
import com.khan.journalapplication.data.remote.JournalRequest
import com.khan.journalapplication.data.remote.JournalResponse
import com.khan.journalapplication.model.Journal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

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

    data class DraftSyncResult(
        val synced: Int,
        val failed: Int
    )
    suspend fun syncDraftJournals(): DraftSyncResult {
        val drafts = journalDao.getDraftJournals()

        var syncedCount = 0
        var failedCount = 0

        drafts.forEach { draft ->
            try {
                val aiResponse = analyzeJournal(draft.content)

                val syncedJournal = draft.copy(
                    mood = aiResponse.mood,
                    supportiveMessage = aiResponse.supportive_message,
                    suggestions = aiResponse.suggestions,
                    isDraft = false
                )

                journalDao.updateJournal(syncedJournal)
                syncedCount++
            } catch (exception: Exception) {
                // Keep this journal as a draft so it can be retried.
                failedCount++
            }
        }

        return DraftSyncResult(
            synced = syncedCount,
            failed = failedCount
        )
    }



}