package com.example.opsc7311.ui.navigation

import com.example.opsc7311.ui.screens.goals.GoalScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.opsc7311.ui.screens.categories.CategoryScreen
import com.example.opsc7311.ui.screens.goals.GoalScreenViewModel
import com.example.opsc7311.ui.screens.list.appbar.FilterBarViewModel
import com.example.opsc7311.ui.screens.stats.StatsScreen
import com.example.opsc7311.ui.screens.stats.StatsScreenViewModel
import com.example.opsc7311.viewmodels.SharedViewModel

object HomeGraph {
    const val CATEGORY_SCREEN = "Category"
    const val GOALS_SCREEN = "Goals"
    const val STATS_SCREEN = "Stats"
}

@Composable
fun HomeNavGraph(
    navController: NavHostController = rememberNavController(),
    sharedViewModel: SharedViewModel,
    filterBarViewModel: FilterBarViewModel,
    goalScreenViewModel: GoalScreenViewModel,
    statsScreenViewModel: StatsScreenViewModel
)
{
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = Graph.TIMESHEETS
    ) {
        // Do not pass navController as it has its own NavHost.
        // Each NavHost must have its own navHostController
        composable(route = Graph.TIMESHEETS) {
            TimesheetNavGraph(
                sharedViewModel = sharedViewModel,
                filterBarViewModel = filterBarViewModel
            )
        }
        composable(route = HomeGraph.CATEGORY_SCREEN) {
            CategoryScreen(
                sharedViewModel = sharedViewModel,
                filterBarViewModel = filterBarViewModel
            )
        }
        composable(route = HomeGraph.GOALS_SCREEN) {
            GoalScreen(
                goalScreenViewModel = goalScreenViewModel,
                sharedViewModel = sharedViewModel
            )
        }
        composable(route = HomeGraph.STATS_SCREEN) {
            StatsScreen(
                statsScreenViewModel = statsScreenViewModel,
                filterBarViewModel = filterBarViewModel,
                sharedViewModel = sharedViewModel
            )
        }
    }
}