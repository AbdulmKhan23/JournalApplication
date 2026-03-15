package com.khan.journalapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.khan.journalapplication.presentation.HomeScreen.HomeScreen
import com.khan.journalapplication.presentation.HomeScreen.HomeViewModel
import com.khan.journalapplication.presentation.JournalScreen.JournalScreen

@Composable
fun JournalNavGraph() {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val journals by homeViewModel.journalList.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                journal = journals,
                onRemoveJournal = { journal ->
                    homeViewModel.removeNote(journal)
                },
                onAddJournalClick = {
                    navController.navigate("journal")
                }
            )
        }
        composable("journal") {
            JournalScreen(
                onAddJournal = { journal ->
                    homeViewModel.addNote(journal)
                    navController.popBackStack()
                }
            )
        }
    }
}

