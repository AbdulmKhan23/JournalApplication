package com.khan.journalapplication.presentation.ReflectScreen


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.khan.journalapplication.R
import com.khan.journalapplication.model.Journal
import com.khan.journalapplication.presentation.components.JournalInputText

@Composable
fun ReflectScreen(
    onAddJournal: (Journal) -> Unit,
    onSaved: () -> Unit,
    viewModel: ReflectViewModel = hiltViewModel()
) {
    val question by viewModel.question.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var answer by remember(question) {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val backgroundColor = colorResource(R.color.app_background)
    val surfaceColor = colorResource(R.color.app_surface)
    val textColor = colorResource(R.color.app_text)
    val accentColor = colorResource(R.color.app_accent)

    LaunchedEffect(error) {
        error?.let { message ->
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = surfaceColor,
            border = BorderStroke(
                width = 2.dp,
                color = textColor
            )
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 20.dp
                ),
                verticalArrangement = Arrangement.Center
            ) {
                if (isLoading && question.isBlank()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = accentColor
                    )
                } else {
                    Text(
                        text = question.ifBlank {
                            "Unable to load a reflection question."
                        },
                        modifier = Modifier.fillMaxWidth(),
                        color = textColor,
                        fontSize = 19.sp,
                        lineHeight = 26.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = viewModel::getNewQuestion,
                enabled = !isLoading
            ) {
                Text(
                    text = "Another question",
                    color = accentColor
                )
            }
        }

        JournalInputText(
            text = answer,
            label = "Write your reflection",
            enabled = !isLoading && question.isNotBlank(),
            readOnly = false,
            onTextChange = { answer = it },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Button(
            onClick = {
                onAddJournal(
                    Journal(
                        title = question,
                        content = answer.trim(),
                        mood = "",
                        supportiveMessage = "",
                        suggestions = emptyList()
                    )
                )

                onSaved()
            },
            enabled = question.isNotBlank() &&
                    answer.isNotBlank() &&
                    !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = textColor,
                contentColor = surfaceColor
            )
        ) {
            Text(
                text = "Save",
                fontSize = 18.sp
            )
        }

        if (error != null && question.isBlank()) {
            OutlinedButton(
                onClick = viewModel::getNewQuestion,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, textColor)
            ) {
                Text(
                    text = "Try again",
                    color = textColor
                )
            }
        }
    }
}
