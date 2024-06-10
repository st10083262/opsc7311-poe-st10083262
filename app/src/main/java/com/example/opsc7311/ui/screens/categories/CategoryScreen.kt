package com.example.opsc7311.ui.screens.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.opsc7311.ui.screens.list.appbar.FilterBar
import com.example.opsc7311.ui.screens.list.appbar.FilterBarViewModel
import com.example.opsc7311.viewmodels.SharedViewModel

@Composable
fun CategoryScreen(
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
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            val sharedViewModelUiState by sharedViewModel.uiState.collectAsState()
            CategoryScreenContent(
                sharedViewModelUiState = sharedViewModelUiState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            )
        }
    }
}