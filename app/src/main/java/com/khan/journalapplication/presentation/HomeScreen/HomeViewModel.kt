package com.khan.journalapplication.presentation.HomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khan.journalapplication.model.Journal
import com.khan.journalapplication.repository.JournalRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: JournalRepo) : ViewModel() {
    private val _journalList = MutableStateFlow<List<Journal>>(emptyList())
    val journalList = _journalList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllJournals().distinctUntilChanged().collect (){listOfJournals ->
                if (listOfJournals.isNullOrEmpty()) {
                    _journalList.value = emptyList()
                } else {
                    _journalList.value = listOfJournals
                }
            }
        }
    }
    fun addNote(journal: Journal) = viewModelScope.launch { repository.addJournal(journal) }
    fun updateNote(journal: Journal) = viewModelScope.launch { repository.updateJournal(journal) }
    fun removeNote(journal: Journal) = viewModelScope.launch { repository.deleteJournal(journal) }
}