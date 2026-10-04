package com.khan.journalapplication.navigation

import androidx.compose.foundation.background
import androidx.compose.ui.res.colorResource
import com.khan.journalapplication.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.khan.journalapplication.model.Journal
import com.khan.journalapplication.presentation.DetailScreen.DetailScreen
import com.khan.journalapplication.presentation.HomeScreen.HomeScreen
import com.khan.journalapplication.presentation.HomeScreen.HomeViewModel
import com.khan.journalapplication.presentation.JournalScreen.JournalScreen
import com.khan.journalapplication.presentation.SearchScreen.SearchScreen

private enum class MainTab(val route: String, val label: String) {
    Home("home", "Home",),
    Search("search", "Search"),
    Profile("profile", "Profile")
}

@Composable
fun JournalNavGraph() {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val journals by homeViewModel.journalList.collectAsState()
    val isOnline by homeViewModel.isOnline.collectAsState()
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()
    val syncMessage by homeViewModel.syncMessage.collectAsState()
    var selectedTab by rememberSaveable { mutableStateOf(MainTab.Home) }
    val appBackground = colorResource(id = R.color.app_background)
    val fabColor = colorResource(id = com.khan.journalapplication.R.color.app_text)

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = appBackground){
                MainTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = {
                            selectedTab = tab
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = when (tab) {
                                    MainTab.Home -> Icons.Default.Home
                                    MainTab.Search -> Icons.Default.Search
                                    MainTab.Profile -> Icons.Default.Person
                                },
                                contentDescription = tab.label
                            )
                        },
                        label = {
                            Text(text = tab.label,
                            fontSize = 12.sp,
                                fontWeight = FontWeight.Bold)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = appBackground
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MainTab.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(MainTab.Home.route) {
                // Intentionally blank for now.
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(appBackground))
                {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate("journal")
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(start = 16.dp, end = 32.dp, top = 16.dp, bottom = 16.dp),
                        containerColor = fabColor,
                        shape = CircleShape,
                        contentColor = colorResource(R.color.white)
                    ){
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add journal entry"
                        )
                    }
                }
            }

            composable(MainTab.Search.route) {
                SearchScreen(
                    journal = journals,
                    isOnline = isOnline,
                    isRefreshing = isRefreshing,
                    syncMessage = syncMessage,
                    onRefresh = homeViewModel::syncDraftJournals,
                    onRemoveJournal = { journal ->
                        homeViewModel.removeJournal(journal)
                    },
                    onJournalClick = { journal ->
                        navController.navigate("details/${journal.id}")
                    }
                )
            }

            composable(MainTab.Profile.route) {
                // Intentionally blank for now.
            }

            composable("journal") {
                JournalScreen(
                    isOnline = isOnline,
                    onAddJournal = { journal ->
                        homeViewModel.addJournal(journal)
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = "details/{journalId}",
                arguments = listOf(
                    navArgument("journalId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                DetailScreen(
                    journalId = backStackEntry.arguments?.getString("journalId")
                )
            }
        }
    }
}

