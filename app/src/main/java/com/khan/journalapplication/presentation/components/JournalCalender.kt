package com.khan.journalapplication.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khan.journalapplication.R
import com.khan.journalapplication.model.Journal
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun JournalCalendar(
    journals: List<Journal>,
    modifier: Modifier = Modifier
) {
    var displayedMonth by remember {
        mutableStateOf(YearMonth.now())
    }

    val calendarBackground = colorResource(R.color.app_background)
    val textColor = colorResource(R.color.app_text)

    // Reflections have no mood, so only journal entries contribute calendar mood colors.
    val moodByDate = journals
        .filter {
            it.mood.isNotBlank() &&
                !it.mood.equals("Reflection", ignoreCase = true)
        }
        .groupBy { journal ->
            Instant.ofEpochMilli(journal.entryDate.time)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        }
        .mapValues { (_, entries) ->
            moodLable(entries.maxBy { it.entryDate.time }.mood)
        }

    val firstDay = displayedMonth.atDay(1)
    val leadingDays = firstDay.dayOfWeek.value % 7 // Sunday is the first column
    val gridStart = firstDay.minusDays(leadingDays.toLong())

    Surface(
        modifier = modifier,
        color = calendarBackground,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, textColor),
        shadowElevation = 2.dp
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { displayedMonth = displayedMonth.minusMonths(1) }) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = "Previous month")
                }

                Text(
                    text = "${displayedMonth.month.getDisplayName(
                        TextStyle.FULL,
                        Locale.getDefault()
                    )} ${displayedMonth.year}",
                    modifier = Modifier.weight(1f),
                    color = textColor,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )

                IconButton(onClick = { displayedMonth = displayedMonth.plusMonths(1) }) {
                    Icon(Icons.Default.ChevronRight, contentDescription = "Next month")
                }
            }

            val weekDays = listOf("S", "M", "T", "W", "T", "F", "S")
            Row(Modifier.fillMaxWidth()) {
                weekDays.forEach { day ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day,
                            color = textColor,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            repeat(6) { week ->
                Row(Modifier.fillMaxWidth()) {
                    repeat(7) { dayOfWeek ->
                        val date = gridStart.plusDays((week * 7L) + dayOfWeek)
                        val isCurrentMonth = YearMonth.from(date) == displayedMonth
                        val moodColor = if (isCurrentMonth) moodByDate[date] else null

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(42.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(moodColor ?: calendarBackground),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = date.dayOfMonth.toString(),
                                    color = if (isCurrentMonth) textColor
                                    else textColor.copy(alpha = 0.35f),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
