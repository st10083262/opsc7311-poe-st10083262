package com.example.opsc7311.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.opsc7311.ui.screens.list.TimesheetListScreen
import com.example.opsc7311.ui.screens.list.appbar.FilterBarViewModel
import com.example.opsc7311.ui.screens.edit.EditScreenViewModel
import com.example.opsc7311.ui.screens.edit.TimesheetEditScreen
import com.example.opsc7311.viewmodels.SharedViewModel

object TimesheetGraph {
    const val LIST_SCREEN = "List"
    const val EDIT_SCREEN = "Edit/{id}"
    const val EDIT_SCREEN_ARGUMENT = "id"
}

@Composable
fun TimesheetNavGraph(
    navController: NavHostController = rememberNavController(),
    sharedViewModel: SharedViewModel = viewModel(),
    filterBarViewModel: FilterBarViewModel = viewModel()
)
{
    NavHost(
        navController = navController,
        startDestination = TimesheetGraph.LIST_SCREEN,
        route = Graph.HOME
    ) {

        composable(
            route = TimesheetGraph.LIST_SCREEN,
        ) {

            TimesheetListScreen(
                onFabClicked = {
                    navController.navigate(
                        route = "Edit/-1"
                    )
                },
                onTimesheetClicked = { id ->
                    navController.navigate(
                        route = "Edit/$id"
                    )
                },
                sharedViewModel = sharedViewModel,
                filterBarViewModel = filterBarViewModel
            )
        }

        composable(
            route = TimesheetGraph.EDIT_SCREEN,
            arguments = listOf(
                navArgument(TimesheetGraph.EDIT_SCREEN_ARGUMENT) {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->

            val id = navBackStackEntry.arguments!!.getString(TimesheetGraph.EDIT_SCREEN_ARGUMENT)
            val timesheet = sharedViewModel.getTimeSheetOrReturnNew(id!!)

            // hacky way to pass parameter to a new view model
            // taken from
            // https://stackoverflow.com/questions/46283981/android-viewmodel-additional-arguments
            // authored by: mlykotom on Oct 12, 2017 at 8:18
            val editScreenViewModel: EditScreenViewModel =
                viewModel(factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return EditScreenViewModel(timesheet) as T
                    }
                })

            TimesheetEditScreen(
                sharedViewModel = sharedViewModel,
                editScreenViewModel = editScreenViewModel,
                onBackPressed = {
                    navController.popBackStack("List", false)
                },
                afterSavePressed = {
                    navController.popBackStack("List", false)
                }
            )
        }
    }
}
