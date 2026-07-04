package com.khan.journalapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.khan.journalapplication.presentation.DetailScreen.DetailScreen
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
                    homeViewModel.removeJournal(journal)
                },
                onAddJournalClick = {
                    navController.navigate("journal")
                },
                onJournalClick = { journal ->
                    navController.navigate("details/${journal.id}")
                }
            )
        }

        composable("journal") {
                    JournalScreen ()
    }
        composable(
            route = "details/{journalId}",
            arguments = listOf(
                navArgument("journalId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val journalId =
                backStackEntry.arguments?.getString("journalId")

            DetailScreen(
                journalId = journalId
            )
        }

    }
}

