package com.khan.journalapplication.presentation.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khan.journalapplication.data.JournalDataSource
import com.khan.journalapplication.model.Journal
import com.khan.journalapplication.presentation.components.JournalList
import com.khan.journalapplication.presentation.components.SearchBar

@Composable
fun HomeScreen(
    journal:List<Journal>,
    onRemoveJournal:(Journal)-> Unit,
    onAddJournalClick: () -> Unit
    ){
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(16.dp)
        ) {
            var searchText = remember { mutableStateOf("") }

            // SEARCH FUNCTION
            SearchBar(
                text = searchText.value,
                onQueryChange = { newText ->
                    searchText.value = newText
                },
                onSearch = {
                    // Perform search functionality
                    println("Searching for: ${searchText.value}")
                },
                placeholder = "Search journal entries..."
            )
            //JOURNAL LIST
            JournalList(modifier = Modifier,
                journals = journal,
                onNoteClicked = { /* TODO: navigate to detail/edit if needed */ },
                onDeleteJournal = { journalToDelete ->
                    onRemoveJournal(journalToDelete)
                })

        }
        //ADD BUTTON
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp))   {
            // Button positioned at bottom using Box
            Box(modifier = Modifier.fillMaxSize()) {
                Button(
                    onClick = { onAddJournalClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Journal",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }


    }

}
@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        journal = JournalDataSource().loadJournal(),
        onRemoveJournal = {},
        onAddJournalClick = {}
    )
}