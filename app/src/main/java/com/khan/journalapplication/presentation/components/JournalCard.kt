package com.khan.journalapplication.presentation.components



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khan.journalapplication.model.Journal
import com.khan.journalapplication.util.formatdate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalCard(
    modifier : Modifier = Modifier,
    onDeleteJournal:(Journal)-> Unit,
    journal: Journal,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .border(1.dp, color = Color.White, shape = RoundedCornerShape(5.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = journal.title,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .border(1.dp, color = Color.White, shape = RoundedCornerShape(5.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = journal.mood,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, end = 8.dp, start = 8.dp)
                    .border(1.dp, color = Color.White, shape = RoundedCornerShape(5.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = journal.content,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 28.dp)
                        .border(1.dp, color = Color.White, shape = RoundedCornerShape(5.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = formatdate(journal.entryDate.time),
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(
                    modifier = Modifier
                        .heightIn(min = 28.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(5.dp))
                        .clickable { onDeleteJournal(journal) }
                        .background(Color.Transparent, RoundedCornerShape(5.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Black,
                            modifier = Modifier.size(14.dp)
                        )
                        Text("DELETE", fontSize = 10.sp, color = Color.Black)
                    }
                }
            }
        }
    }
}






@Preview
@Composable
fun ArticleCardPreview(){
    JournalCard(journal = Journal(title = "Hello", content = "The sun dipped slowly behind the hills, painting the sky in shades of orange and pink. A gentle breeze rustled through the tall grass, carrying the scent of wildflowers. Birds chirped their final songs of the day as " +
            "the world grew quieter. The golden light touched everything it could reach, turning even the smallest " +
            "pebble into something beautiful. It was a moment of calm, the kind that made you forget time for a while.", mood = "Happy"), onClick = {}, onDeleteJournal = {})
}