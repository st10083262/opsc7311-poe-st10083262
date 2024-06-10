package com.example.opsc7311.ui.screens.list.appbar

import com.maxkeppeker.sheets.core.models.base.UseCaseState

data class FilterBarUiState(
    val startDate: String = "Start Date",
    val endDate: String = "End Date",
    val startDateFilterExpanded: Boolean = false,
    val endDateFilterExpanded: Boolean = false,
    val startDatePickerState: UseCaseState = UseCaseState(),
    val endDatePickerState: UseCaseState = UseCaseState()
)
