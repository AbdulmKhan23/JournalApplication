package com.khan.journalapplication.presentation.DetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.khan.journalapplication.presentation.components.JournalInputText
import com.khan.journalapplication.presentation.components.SuggestionsCard

@Composable
fun DetailScreen(
    journalId:String?
){

    val viewModel: DetailViewModel = hiltViewModel()

    val journal by viewModel.journal.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(journalId) {
        journalId?.let {
            viewModel.loadJournal(it)
        }
    }




    LazyColumn(modifier = Modifier.fillMaxSize()
        .windowInsetsPadding(WindowInsets.safeDrawing),
        contentPadding= PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
        )
    {

        journal?.let {
            item {
                JournalInputText(
                    text = it.title,
                    label= "Title",
                    enabled =true,
                    readOnly = true,
                    onTextChange ={},
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item{
                JournalInputText(
                    text = it.content,
                    label= "Content",
                    enabled =true,
                    readOnly = true,
                    onTextChange ={},
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item{
                SuggestionsCard(mood = it.mood,
                    supportiveMessage = it.supportiveMessage,
                    suggestions = it.suggestions)

            }


        }



    }
}

