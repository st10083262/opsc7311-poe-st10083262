package com.example.opsc7311.ui.screens.goals

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.opsc7311.viewmodels.SharedViewModel

@Composable
fun GoalScreen(
    goalScreenViewModel: GoalScreenViewModel,
    sharedViewModel: SharedViewModel
) {
    val goalScreenUiState by goalScreenViewModel.uiState.collectAsState()

    GoalScreenContent(
        goalScreenUiState = goalScreenUiState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()
            ),
        onMinDailyGoalSliderChanged = {
            goalScreenViewModel.setMinDailyGoalSliderValue(it)
        },
        onMinDailyGoalSliderFinishChange = {
            goalScreenViewModel.updateMinDailyGoal()
            sharedViewModel.updateMinDailyGoal(goalScreenViewModel.uiState.value.minDailyGoal)
        },
        onMaxDailyGoalSliderChanged = {
            goalScreenViewModel.setMaxDailyGoalSliderValue(it)
        },
        onMaxDailyGoalSliderFinishChange = {
            goalScreenViewModel.updateMaxDailyGoal()
            sharedViewModel.updateMaxDailyGoal(goalScreenViewModel.uiState.value.maxDailyGoal)
        }
    )
}