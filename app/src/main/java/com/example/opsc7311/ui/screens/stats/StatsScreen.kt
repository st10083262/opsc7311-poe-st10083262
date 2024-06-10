package com.example.opsc7311.ui.screens.stats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.opsc7311.ui.screens.list.appbar.FilterBar
import com.example.opsc7311.ui.screens.list.appbar.FilterBarViewModel
import com.example.opsc7311.viewmodels.SharedViewModel
import java.util.logging.Filter

@Composable
fun StatsScreen(
    statsScreenViewModel: StatsScreenViewModel,
    filterBarViewModel: FilterBarViewModel,
    sharedViewModel: SharedViewModel
) {

    val statsScreenUiState by statsScreenViewModel.uiState.collectAsState()
    val sharedViewModelUiState by sharedViewModel.uiState.collectAsState()

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
        }
    ) {
        Box(modifier = Modifier.padding(it))
        {
            StatsScreenContent(
                statsScreenUiState = statsScreenUiState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(
                        rememberScrollState()
                    ),
                data = sharedViewModel.calculateTotalDurations(),
                minGoal = sharedViewModelUiState.minDailyGoal,
                maxGoal = sharedViewModelUiState.maxDailyGoal
            )
        }
    }

}