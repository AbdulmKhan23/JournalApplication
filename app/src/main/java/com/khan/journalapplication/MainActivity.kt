package com.khan.journalapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.khan.journalapplication.ui.theme.JournalApplicationTheme
import com.khan.journalapplication.navigation.JournalNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JournalApplicationTheme {
                JournalNavGraph()
            }
        }
    }
}


