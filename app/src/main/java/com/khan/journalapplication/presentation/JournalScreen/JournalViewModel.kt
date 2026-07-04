package com.khan.journalapplication.presentation.JournalScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khan.journalapplication.data.repository.JournalRepo
import com.khan.journalapplication.model.Journal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val repository: JournalRepo
) : ViewModel(){

    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions: StateFlow<List<String>> = _suggestions

    private val _supportiveMessage = MutableStateFlow("")
    val supportiveMessage: StateFlow<String> = _supportiveMessage

    private val _mood = MutableStateFlow("")
    val mood: StateFlow<String> = _mood

    private val _showSuggestions = MutableStateFlow(false)
    val showSuggestions: StateFlow<Boolean> = _showSuggestions

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _canSave = MutableStateFlow(true)
    val canSave: StateFlow<Boolean> = _canSave


    private var lastTitle = ""
    private var lastContent = ""

    fun onJournalChanged(
        title: String,
        content: String
    ) {
        _canSave.value =
            title != lastTitle ||
                    content != lastContent
    }



    fun saveJournal(
        title: String,
        content: String
    ) {

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null

            try {

                // Call FastAPI -> Gemini
                val aiResponse = repository.analyzeJournal(content)

                // Update UI
                _mood.value = aiResponse.mood
                _supportiveMessage.value = aiResponse.supportive_message
                _suggestions.value = aiResponse.suggestions
                _showSuggestions.value = true

                // Save to Room
                val journal = Journal(
                    title = title,
                    content = content,
                    mood = aiResponse.mood,
                    supportiveMessage = aiResponse.supportive_message,
                    suggestions = aiResponse.suggestions
                )

                repository.addJournal(journal)

                // Remember last analyzed text
                lastTitle = title
                lastContent = content

                // Disable Save until something changes
                _canSave.value = false

            } catch (e: Exception) {

                _error.value = e.message ?: "Something went wrong."

            } finally {

                _isLoading.value = false

            }

        }

    }
}

