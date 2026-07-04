package com.khan.journalapplication.presentation.JournalScreen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.khan.journalapplication.presentation.components.JournalInputText
import com.khan.journalapplication.presentation.components.SuggestionsCard

@Composable
fun JournalScreen() {

    val viewModel : JournalViewModel= hiltViewModel()

    var title by remember {
        mutableStateOf("")
    }
    var content by remember {
        mutableStateOf("")
    }

    val mood by viewModel.mood.collectAsState()
    val supportiveMessage by viewModel.supportiveMessage.collectAsState()
    val suggestions by viewModel.suggestions.collectAsState()
    val showSuggestions by viewModel.showSuggestions.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val canSave by viewModel.canSave.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_LONG
            ).show()
        }
    }

        Column(modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .verticalScroll(rememberScrollState())
        ) {

            //TITLE
            JournalInputText(
                text = title,
                label = "Title",
                enabled = true,
                readOnly = false,
                onTextChange = {
                    title = it
                    viewModel.onJournalChanged(title,content)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )

            //CONTENT
            JournalInputText(
                text = content,
                label = "Content",
                enabled = true,
                readOnly = false,
                onTextChange = {
                    content = it
                    viewModel.onJournalChanged(title,content)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            //MOOD, SUPPORTIVE MESSAGE, SUGGESTIONS
            AnimatedVisibility(
                visible = showSuggestions,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut()
            )
            {
                SuggestionsCard(supportiveMessage = supportiveMessage, suggestions =  suggestions, mood = mood,
                    modifier= Modifier.padding(horizontal = 12.dp))
            }

                //SAVE BUTTON
                Button(
                    onClick = {viewModel.saveJournal(
                        title=title,
                        content=content
                    )
                    },
                    enabled = !isLoading && canSave,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(12.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Save")
                    }
                }
        }
}


@Preview(showBackground = true)
@Composable
fun JournalScreenPreview(){
    JournalScreen()
}