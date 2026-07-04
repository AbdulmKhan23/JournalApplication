package com.khan.journalapplication.presentation.DetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khan.journalapplication.data.repository.JournalRepo
import com.khan.journalapplication.model.Journal
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: JournalRepo
): ViewModel(){

    private val _journal = MutableStateFlow<Journal?>(null)
    val journal: StateFlow<Journal?> = _journal

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadJournal(id: String?){

        if (id == null) return

        viewModelScope.launch{

            _isLoading.value=true
            _error.value=null

            try{
                _journal.value = repository.getJournalById(id)
            }catch (e:Exception){
                _error.value=e.message?:"Unable to load journal."
            } finally {
                _isLoading.value = false
            }
        }
    }

}
