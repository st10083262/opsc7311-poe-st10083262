package com.example.opsc7311.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.opsc7311.ui.screens.list.appbar.FilterBar
import com.example.opsc7311.ui.screens.list.appbar.FilterBarViewModel
import com.example.opsc7311.viewmodels.SharedViewModel

@Composable
fun ListScreen(
    sharedViewModel: SharedViewModel,
    onTimesheetClicked: (String) -> Unit,
    onImageClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val sharedViewModelUiState by sharedViewModel.uiState.collectAsState()

    ListScreenContent(
        sharedViewModelUiState = sharedViewModelUiState,
        onTimesheetClicked = onTimesheetClicked,
        onImageClicked = onImageClicked,
        modifier = modifier
    )

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TimesheetListScreen(
    onFabClicked: () -> Unit,
    onTimesheetClicked: (String) -> Unit,
    sharedViewModel: SharedViewModel,
    filterBarViewModel: FilterBarViewModel
)
{
    

    Scaffold(
        topBar = {
            FilterBar(
                filterBarViewModel = filterBarViewModel,
                onStartDateSelected = {date->
                    filterBarViewModel.updateStartDate(date.toString())
                    sharedViewModel.setStartDate(date.toString())
                    filterBarViewModel.setStartDateFilterExpanded(false)
                    filterBarViewModel.setShowStartDatePicker(false)

                },
                onEndDateSelected = { date->
                    filterBarViewModel.updateEndDate(date.toString())

                    sharedViewModel.setEndDate(date.toString())
                    filterBarViewModel.setEndDateFilterExpanded(false)
                    filterBarViewModel.setShowEndDatePicker(false)
                },
                onResetButtonClick = {
                    filterBarViewModel.reset()
                    sharedViewModel.resetFilter()
                }
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = onFabClicked)
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            ListScreen(
                sharedViewModel = sharedViewModel,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                onTimesheetClicked = onTimesheetClicked,
                onImageClicked = {
                    // Do not do anything
                }
            )
        }
    }
}

@Composable
fun ListFab(
    onFabClicked: () -> Unit
)
{
    FloatingActionButton(
        onClick = onFabClicked,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Add FAB",
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}


@Preview
@Composable
fun ListPreview()
{
    TimesheetListScreen(
        onFabClicked = {},
        onTimesheetClicked = {},
        sharedViewModel = viewModel(),
        filterBarViewModel = viewModel()
    )
}