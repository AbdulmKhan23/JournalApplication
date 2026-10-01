package com.khan.journalapplication.presentation.HomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khan.journalapplication.model.Journal
import com.khan.journalapplication.data.repository.JournalRepo
import com.khan.journalapplication.util.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: JournalRepo,
    networkMonitor: NetworkMonitor
) : ViewModel() {
    val isOnline = networkMonitor.isOnline

    private val _journalList = MutableStateFlow<List<Journal>>(emptyList())
    val journalList: StateFlow<List<Journal>> = _journalList

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _syncMessage = MutableStateFlow<String?>(null)
    val syncMessage: StateFlow<String?> = _syncMessage

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

    fun syncDraftJournals() {
        if (_isRefreshing.value) return

        if (!isOnline.value) {
            _syncMessage.value =
                "No internet connection. Your drafts will remain saved."
            return
        }

        viewModelScope.launch {
            _isRefreshing.value = true
            _syncMessage.value = null

            try {
                val result = repository.syncDraftJournals()

                _syncMessage.value = when {
                    result.synced == 0 && result.failed == 0 ->
                        "All journals are already synced."

                    result.failed == 0 ->
                        "${result.synced} draft journal(s) synced."

                    result.synced == 0 ->
                        "Drafts could not be synced. Please try again."

                    else ->
                        "${result.synced} synced and ${result.failed} could not be synced."
                }
            } catch (exception: Exception) {
                _syncMessage.value =
                    "Drafts could not be synced. Please try again."
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}