package com.khan.journalapplication.presentation.ReflectScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khan.journalapplication.data.repository.JournalRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ReflectViewModel @Inject constructor(
    private val repository: JournalRepo
) : ViewModel() {

    private val _question = MutableStateFlow("")
    val question: StateFlow<String> = _question.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        getNewQuestion()
    }

    fun getNewQuestion() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val response = repository.getReflectQuestion()
                _question.value = response.question
            } catch (_: IOException) {
                _error.value =
                    "Unable to connect. Check your internet connection."
            } catch (exception: HttpException) {
                _error.value = when (exception.code()) {
                    429 -> "Please wait before requesting another question."
                    503 -> "Reflection questions are temporarily unavailable."
                    else -> "Unable to get a reflection question."
                }
            } catch (_: Exception) {
                _error.value =
                    "Something went wrong while creating your question."
            } finally {
                _isLoading.value = false
            }
        }
    }
}