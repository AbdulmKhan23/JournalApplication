package com.khan.journalapplication.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SuggestionsCard(
    modifier: Modifier= Modifier,
                    supportiveMessage:String,
                    mood: String,
                    suggestions: List<String>) {
    Surface(shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(moodLable(mood),CircleShape)
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ){

                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {

                    Text(
                        text = "Detected Mood",
                        style = MaterialTheme.typography.labelMedium
                    )

                    Text(
                        text = mood,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Message",
                style = MaterialTheme.typography.titleMedium
            )


            Text(
                text = supportiveMessage,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "AI Suggestions",
                style = MaterialTheme.typography.titleMedium
            )
            suggestions.forEach {
                Text(
                    text = "• $it",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Preview
@Composable
fun CardPreview(){
    SuggestionsCard( suggestions = listOf("write everything","enjoy this day"), supportiveMessage = "you are doing great",mood="happy")
}