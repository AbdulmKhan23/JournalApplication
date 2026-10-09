package com.khan.journalapplication.presentation.HomeScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khan.journalapplication.data.remote.DailyQuoteResponse
import com.khan.journalapplication.data.remote.JournalApi
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DailyQuoteViewModel @Inject constructor(
    private val journalApi: JournalApi,
    @ApplicationContext context: Context
) : ViewModel() {

    private val quotePreferences = context.getSharedPreferences(
        "daily_quote_cache",
        Context.MODE_PRIVATE
    )

    private val _quote = MutableStateFlow<DailyQuoteResponse?>(null)
    val quote: StateFlow<DailyQuoteResponse?> = _quote.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadQuote()
    }

    fun loadQuote() {
        viewModelScope.launch(Dispatchers.IO) {
            val today = LocalDate.now().toString()
            val cachedDate = quotePreferences.getString(KEY_DATE, null)
            val cachedQuote = quotePreferences.getString(KEY_QUOTE, null)

            if (cachedDate == today && !cachedQuote.isNullOrBlank()) {
                _quote.value = DailyQuoteResponse(date = today, quote = cachedQuote)
                return@launch
            }

            _isLoading.value = true
            _error.value = null

            try {
                val dailyQuote = journalApi.getDailyQuote(date = today)
                _quote.value = dailyQuote
                quotePreferences.edit()
                    .putString(KEY_DATE, dailyQuote.date)
                    .putString(KEY_QUOTE, dailyQuote.quote)
                    .apply()
            } catch (exception: Exception) {
                _error.value = "Couldn't load today's quote."
            } finally {
                _isLoading.value = false
            }
        }
    }

    private companion object {
        const val KEY_DATE = "date"
        const val KEY_QUOTE = "quote"
    }
}
