package com.khan.journalapplication.presentation.components



import android.R
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    val appBackground = colorResource(id = com.khan.journalapplication.R.color.app_background)
    val border = colorResource(id = com.khan.journalapplication.R.color.app_text)

    Card(
        colors = CardDefaults.cardColors(
        containerColor = appBackground),
        modifier = modifier
            .padding(bottom = 4.dp, top = 4.dp)
            .border(4.dp,  color = border, shape = RoundedCornerShape(10.dp) )
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = journal.title,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(modifier= Modifier
                    .wrapContentSize()
                    .padding(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(moodLable(journal.mood),CircleShape)
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ){

                    }
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = journal.content,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 28.dp)
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = formatdate(journal.entryDate.time),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(
                    modifier = Modifier
                        .heightIn(min = 28.dp)
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
                            modifier = Modifier.size(24.dp)
                        )

                    }
                }
            }
        }
    }
}



@Composable
public fun moodLable(mood:String): Color {
    return when(mood.lowercase()){
        "happy" -> Color(0xFFFFDF00)
        "calm" -> Color(0xFF00E85A)
        "sad" -> Color(0xFF4683D5)
        "anxious" -> Color(0xFFFF844D)
        "angry" -> Color(0xFFB51B0A)
        "neutral" -> Color(0xFF5A2B0C)
        else -> Color.Gray
    }
}



@Preview
@Composable
fun ArticleCardPreview(){
    JournalCard(journal = Journal(
        title = "Hello",
        content = "The sun dipped slowly behind the hills, painting the sky in shades of orange and pink. A gentle breeze rustled through the tall grass, carrying the scent of wildflowers. Birds chirped their final songs of the day as " +
                "the world grew quieter. The golden light touched everything it could reach, turning even the smallest " +
                "pebble into something beautiful. It was a moment of calm, the kind that made you forget time for a while.",
        mood = "Happy",
        supportiveMessage = "You had a very calm day ",
        suggestions = listOf("take a break ","plan tomorrow")
    ), onClick = {}, onDeleteJournal = {})
}