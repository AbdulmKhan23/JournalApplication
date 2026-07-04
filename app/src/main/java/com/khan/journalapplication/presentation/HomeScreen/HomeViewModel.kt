package com.khan.journalapplication.presentation.HomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khan.journalapplication.model.Journal
import com.khan.journalapplication.data.repository.JournalRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: JournalRepo) : ViewModel() {
    private val _journalList = MutableStateFlow<List<Journal>>(emptyList())
    val journalList: StateFlow<List<Journal>> = _journalList

    init {
        viewModelScope.launch() {
            repository.getAllJournals().collect (){journals ->
                _journalList.value=journals

            }
        }
    }
    fun addJournal(journal: Journal) = viewModelScope.launch { repository.addJournal(journal) }
    fun updateJournal(journal: Journal) = viewModelScope.launch { repository.updateJournal(journal) }
    fun removeJournal(journal: Journal) = viewModelScope.launch { repository.deleteJournal(journal) }
}