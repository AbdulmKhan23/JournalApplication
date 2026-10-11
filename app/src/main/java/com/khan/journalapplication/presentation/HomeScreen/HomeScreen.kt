package com.khan.journalapplication.presentation.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.khan.journalapplication.R
import com.khan.journalapplication.model.Journal
import com.khan.journalapplication.presentation.components.BreatheButton
import com.khan.journalapplication.presentation.components.DailyQuoteCard
import com.khan.journalapplication.presentation.components.JournalCalendar
import com.khan.journalapplication.presentation.components.ReflectButton
import com.khan.journalapplication.util.greeting

@Composable
fun HomeScreen(
    journal:List<Journal>,
    onBreatheClick: () -> Unit,
    onReflectClick: () -> Unit
)
{
    val appBackground = colorResource(id = R.color.app_background)
    val textColor = colorResource(id = R.color.app_text)

    val quoteViewModel: DailyQuoteViewModel = hiltViewModel()
    val quote by quoteViewModel.quote.collectAsState()

    Column(
            modifier = Modifier
                .background(appBackground)
                .padding(16.dp)
        ) {
            Text(modifier = Modifier.padding(8.dp),
                text = greeting("Abdul"),
                color = textColor,
                fontSize = 36.sp,
                style = MaterialTheme.typography.headlineMedium
            )

        Spacer(Modifier.height(24.dp))

        JournalCalendar(journals = journal)

        Spacer(Modifier.height(16.dp))

        quote?.let {
            DailyQuoteCard(quote = it.quote)
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BreatheButton(
                onClick = onBreatheClick,
                modifier = Modifier.weight(1f)
            )

            ReflectButton(
                onClick = onReflectClick,
                modifier = Modifier.weight(1f)
            )
        }

    }

}





