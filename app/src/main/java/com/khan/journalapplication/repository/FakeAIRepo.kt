package com.khan.journalapplication.repository

import com.khan.journalapplication.model.AISuggestions

class FakeAIRepo {

    fun getSuggestions(): AISuggestions{
        return AISuggestions(
            suggestions = listOf(
                "Take 10 min walk",
                "Drink water every hour",
                "Take a nap",
                "Read a book")
            )
    }
}