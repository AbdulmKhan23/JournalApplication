package com.khan.journalapplication.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface JournalApi {

    @POST("analyze")
    suspend fun analyzeJournal(
        @Body request: JournalRequest
    ): JournalResponse

    @GET("daily-quote")
    suspend fun getDailyQuote(
        @Query("date") date: String
    ): DailyQuoteResponse
}