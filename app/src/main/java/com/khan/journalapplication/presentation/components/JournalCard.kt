package com.khan.journalapplication.presentation.components



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
            .width(315.dp)
            .clickable { onClick() }) {
        Column(modifier = Modifier.fillMaxWidth()
        , verticalArrangement = Arrangement.Top) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // text row
            Row(
                modifier = Modifier.padding(8.dp)
                    .size(
                        width = 180.dp,
                        height = 25.dp
                    ).border(
                        1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(5.dp)
                    )
            ) {
                Text(
                    modifier = modifier.padding(5.dp),
                    text = journal.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            //Mood row
            Row(
                modifier = Modifier.padding(top = 8.dp, end = 8.dp)
                    .size(width = 120.dp, height = 25.dp)
                    .border(
                        1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(5.dp)
                    ),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(modifier = modifier.padding(top = 4.dp), text = journal.mood)
            }

        }
        //Content Column
        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(top = 1.dp, end = 8.dp, start = 8.dp, bottom = 8.dp)
                .border(
                    1.dp, color = Color.White,
                    shape = RoundedCornerShape(5.dp)
                )
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = journal.content,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
        //TIME
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp))
        {
            Row(modifier = Modifier.padding(start = 8.dp).size(width = 180.dp,
                height = 25.dp)
                    .border(
                        1.dp, color = Color.White,
                        shape = RoundedCornerShape(5.dp)))
            {
                Text(
                    modifier = Modifier.padding(top = 5.dp, start = 8.dp, bottom = 5.dp),
                    text = formatdate(journal.entryDate.time),
                )

            }

            //DELETE BUTTON
            Row(modifier = Modifier.fillMaxWidth().padding(start = 5.dp, end = 8.dp))
            {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(25.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(5.dp))
                        .clickable {
                            onDeleteJournal(journal)
                        }
                        .background(Color.Transparent, RoundedCornerShape(5.dp)),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row (verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 6.dp)){
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Black,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier= Modifier.width(20.dp))
                        Text("DELETE", fontSize = 10.sp, color = Color.Black)
                    }

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