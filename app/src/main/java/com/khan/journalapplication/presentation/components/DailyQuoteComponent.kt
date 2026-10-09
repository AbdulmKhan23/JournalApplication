package com.khan.journalapplication.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.khan.journalapplication.R


@Composable
fun DailyQuoteCard(
    quote: String,
    modifier: Modifier = Modifier
) {
    val background = colorResource(R.color.app_background)
    val textColor = colorResource(R.color.app_text)

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = background,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, textColor)
    ) {
        Text(
            text = quote,
            modifier = Modifier.padding(14.dp),
            color = textColor,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
