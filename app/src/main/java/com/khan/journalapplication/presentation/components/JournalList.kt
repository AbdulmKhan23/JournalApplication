package com.khan.journalapplication.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khan.journalapplication.model.Journal


@Composable
fun JournalList(
    modifier: Modifier = Modifier,
    journals: List<Journal>,
    onJournalClicked: (Journal) -> Unit,
    onDeleteJournal: (Journal) -> Unit)
{
    LazyColumn(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(10.dp)) {

        items(items=journals,
            key={it.id}) {journal ->
            JournalCard(
                journal = journal,
                onClick = { onJournalClicked(journal) },
                onDeleteJournal = { onDeleteJournal(journal) }
            )
        }
    }
}