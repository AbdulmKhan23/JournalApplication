package com.khan.journalapplication.presentation.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khan.journalapplication.model.Journal
import com.khan.journalapplication.presentation.components.JournalList
import com.khan.journalapplication.presentation.components.SearchBar

@Composable
fun HomeScreen(
    journal:List<Journal>,
    onAddJournalClick: () -> Unit,
    onRemoveJournal: (Journal) -> Unit,
    onJournalClick:(Journal)-> Unit
    ){
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(16.dp)
        ) {

            var searchText by rememberSaveable { mutableStateOf("") }

            val filteredJournals = remember(searchText, journal) {
                if (searchText.isBlank()) {
                    journal
                } else {
                    journal.filter {
                        it.title.contains(searchText, ignoreCase = true)
                    }
                }
            }

            var journalToDelete by remember {
                mutableStateOf<Journal?>(null)
            }
            if (journalToDelete != null) {

                AlertDialog(
                    onDismissRequest = {
                        journalToDelete = null
                    },

                    title = {
                        Text("Delete Journal")
                    },

                    text = {
                        Text("Are you sure you want to delete this journal?")
                    },

                    confirmButton = {

                        TextButton(
                            onClick = {
                                onRemoveJournal(journalToDelete!!)
                                journalToDelete = null
                            }
                        ) {
                            Text("Delete")
                        }

                    },

                    dismissButton = {

                        TextButton(
                            onClick = {
                                journalToDelete = null
                            }
                        ) {
                            Text("Cancel")
                        }

                    }

                )

            }

            // SEARCH FUNCTION
            SearchBar(
                text = searchText,
                onQueryChange = {
                    searchText=it
                },
                placeholder = "Search journal entries..."
            )
            //JOURNAL LIST
            JournalList(
                journals=filteredJournals,
                onJournalClicked = onJournalClick,
                onDeleteJournal = {journal ->
                    journalToDelete = journal
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
