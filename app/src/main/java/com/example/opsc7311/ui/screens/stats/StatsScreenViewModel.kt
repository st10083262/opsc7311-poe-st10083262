package com.example.opsc7311.ui.screens.stats

import androidx.lifecycle.ViewModel
import com.example.opsc7311.ui.screens.goals.GoalScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StatsScreenViewModel : ViewModel() {

    // UI State
    private val _uiState = MutableStateFlow(StatsScreenUiState())
    val uiState: StateFlow<StatsScreenUiState> = _uiState.asStateFlow()

}