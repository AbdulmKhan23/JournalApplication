package com.khan.journalapplication.data.remote

data class JournalResponse(
    val mood: String,
    val supportive_message: String,
    val suggestions: List<String>
)