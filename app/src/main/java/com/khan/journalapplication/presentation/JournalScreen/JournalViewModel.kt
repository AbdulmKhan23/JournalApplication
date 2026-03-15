package com.khan.journalapplication.presentation.JournalScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khan.journalapplication.repository.FakeAIRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JournalViewModel : ViewModel() {
    private val  AIrepository  = FakeAIRepo()

    private val _suggestion = MutableStateFlow<List<String>>(emptyList())
    val suggestion: StateFlow<List<String>> = _suggestion

    private val _showSuggestions = MutableStateFlow(false)
    val showSuggestions: StateFlow<Boolean> = _showSuggestions

    fun saveJournal() {
        // Later: save journal to Room

        // Generate AI suggestions
        val result = AIrepository.getSuggestions()
        _suggestion.value = result.suggestions
        _showSuggestions.value = true
    }
}

