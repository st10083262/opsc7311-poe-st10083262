package com.example.opsc7311.ui.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.opsc7311.R
import com.example.opsc7311.ui.models.Timesheet
import com.example.opsc7311.ui.navigation.Graph
import com.example.opsc7311.ui.navigation.HomeGraph
import com.example.opsc7311.ui.navigation.HomeNavGraph
import com.example.opsc7311.ui.screens.goals.GoalScreenViewModel
import com.example.opsc7311.ui.screens.list.appbar.FilterBarViewModel
import com.example.opsc7311.ui.screens.stats.StatsScreenViewModel
import com.example.opsc7311.viewmodels.SharedViewModel
import kotlin.math.roundToInt

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { PaddingValues ->
        Box(
            modifier = Modifier.padding(PaddingValues)
        ) {
            val sharedViewModel: SharedViewModel = viewModel()
            val filterBarViewModel: FilterBarViewModel = viewModel()
            val goalScreenViewModel: GoalScreenViewModel = viewModel()
            val statsScreenViewModel: StatsScreenViewModel = viewModel()

            goalScreenViewModel.updateHoursWorkedToday(sharedViewModel.totalHoursSpentToday().roundToInt())

            HomeNavGraph(
                navController = navController,
                sharedViewModel = sharedViewModel,
                filterBarViewModel = filterBarViewModel,
                goalScreenViewModel = goalScreenViewModel,
                statsScreenViewModel = statsScreenViewModel
            )
        }
    }
}

/*suspend fun LoadTimesheets(): List<Timesheet> {
    //val db = Fire
}*/


@Composable
fun BottomNavBar(
    navController: NavHostController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.clock),
                    contentDescription = null
                )
            },
            label = { Text("Timesheets") },
            selected = currentDestination?.hierarchy?.any { it.route == Graph.TIMESHEETS } == true,
            onClick = {
                navController.popBackStack(Graph.HOME, true)
                navController.navigate(route = Graph.TIMESHEETS)

            },
            alwaysShowLabel = false
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.category),
                    contentDescription = null
                )
            },
            label = { Text("Categories") },
            selected = currentDestination?.hierarchy?.any { it.route == HomeGraph.CATEGORY_SCREEN } == true,
            onClick = {
                navController.popBackStack(Graph.HOME, true)
                navController.navigate(route = HomeGraph.CATEGORY_SCREEN)

            },
            alwaysShowLabel = false
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null
                )
            },
            label = { Text("Goals") },
            selected = currentDestination?.hierarchy?.any { it.route == HomeGraph.GOALS_SCREEN } == true,
            onClick = {
                navController.popBackStack(Graph.HOME, true)
                navController.navigate(route = HomeGraph.GOALS_SCREEN)

            },
            alwaysShowLabel = false
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.graph),
                    contentDescription = null
                )
            },
            label = { Text("Stats") },
            selected = currentDestination?.hierarchy?.any { it.route == HomeGraph.STATS_SCREEN } == true,
            onClick = {
                navController.popBackStack(Graph.HOME, true)
                navController.navigate(route = HomeGraph.STATS_SCREEN)
            },
            alwaysShowLabel = false
        )
    }
}