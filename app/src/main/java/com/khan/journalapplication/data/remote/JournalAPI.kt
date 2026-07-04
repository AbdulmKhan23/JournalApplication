package com.khan.journalapplication.data.remote

import retrofit2.http.Body
import retrofit2.http.POST


interface JournalApi {

    @POST("analyze")
    suspend fun analyzeJournal(
        @Body request: JournalRequest
    ): JournalResponse
}