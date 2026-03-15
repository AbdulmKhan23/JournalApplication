package com.khan.journalapplication.presentation.JournalScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khan.journalapplication.model.Journal
import com.khan.journalapplication.presentation.components.JournalInputText
import com.khan.journalapplication.presentation.components.SuggestionsCard


@Composable
fun JournalScreen(
    onAddJournal: (Journal) -> Unit
) {
    val viewModel = remember { JournalViewModel() }
    var title = remember {
        mutableStateOf("")
    }
    var content = remember {
        mutableStateOf("")
    }

    val suggestions by viewModel.suggestion.collectAsState()
        val showSuggestions by viewModel.showSuggestions.collectAsState()


        Column(modifier = Modifier.fillMaxSize()) {
            JournalInputText(
                text = title, label = "Title", onTextChange = {
                    if (it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        }) title.value = it
                },
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            )


            JournalInputText(
                text = content, label = "Content", onTextChange = {
                    if (it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        }) content.value = it
                },
                modifier = Modifier.fillMaxWidth().weight(1f)
                    .padding(start = 12.dp, end=12.dp)
            )

            AnimatedVisibility(
                visible = showSuggestions,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut()
            ) {
                SuggestionsCard(suggestions)
            }

            Button(
                onClick = {
                    val journal = Journal(
                        title = title.value,
                        content = content.value,
                        mood = "Neutral"
                    )
                    onAddJournal(journal)
                    viewModel.saveJournal()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(12.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = "Save")
            }
        }


}

@Preview(showBackground = true)
@Composable
fun JournalScreenPreview(){
    JournalScreen(onAddJournal = {})
}