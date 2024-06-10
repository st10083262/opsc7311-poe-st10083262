package com.example.opsc7311.ui.screens.list.appbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.opsc7311.util.DateCalPicker
import java.time.LocalDate


@Composable
fun FilterBar(
    filterBarViewModel: FilterBarViewModel,
    onStartDateSelected: (LocalDate) -> Unit,
    onEndDateSelected: (LocalDate) -> Unit,
    onResetButtonClick: () -> Unit
) {

    val filterBarUiState by filterBarViewModel.uiState.collectAsState()

    FilterBarContent(
        filterBarUiState = filterBarUiState,
        onStartDateSelected = onStartDateSelected,
        onEndDateSelected = onEndDateSelected,
        onStartDateCancelClick = {
            filterBarViewModel.setStartDateFilterExpanded(false)
            filterBarViewModel.setShowStartDatePicker(false)
        },
        onEndDateCancelClick = {
            filterBarViewModel.setEndDateFilterExpanded(false)
            filterBarViewModel.setShowEndDatePicker(false)
        },
        onStartDateButtonClick = {
            filterBarViewModel.setStartDateFilterExpanded(true)
            filterBarViewModel.setShowStartDatePicker(true)
        },
        onEndDateButtonClick = {
            filterBarViewModel.setEndDateFilterExpanded(true)
            filterBarViewModel.setShowEndDatePicker(true)
        },
        onResetButtonClick = onResetButtonClick
    )


}

@Composable
fun FilterBarContent(
    filterBarUiState: FilterBarUiState,
    onStartDateSelected: (LocalDate) -> Unit,
    onEndDateSelected: (LocalDate) -> Unit,
    onStartDateCancelClick: () -> Unit,
    onEndDateCancelClick: () -> Unit,
    onStartDateButtonClick: () -> Unit,
    onEndDateButtonClick: () -> Unit,
    onResetButtonClick: () -> Unit
) {
    // Start Date picker
    DateCalPicker(
        calendarState = filterBarUiState.startDatePickerState,
        onSelectDate = onStartDateSelected,
        onNegativeClick = onStartDateCancelClick
    )

    // End Date picker
    DateCalPicker(
        calendarState = filterBarUiState.endDatePickerState,
        onSelectDate = onEndDateSelected,
        onNegativeClick = onEndDateCancelClick

    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        FilterBarDateItem(
            text = filterBarUiState.startDate,
            expanded = filterBarUiState.startDateFilterExpanded,
            onClick = onStartDateButtonClick
        )

        Spacer(modifier = Modifier.padding(horizontal = 4.dp))

        FilterBarDateItem(
            text = filterBarUiState.endDate,
            expanded = filterBarUiState.endDateFilterExpanded,
            onClick = onEndDateButtonClick
        )

        Spacer(modifier = Modifier.padding(horizontal = 4.dp))

        FilterBarItem(
            text = "Reset",
            onClick = onResetButtonClick
        )
    }
}

@Preview
@Composable
fun PreviewFilterBarContent() {
    FilterBarContent(
        filterBarUiState = FilterBarUiState(),
        onStartDateSelected = {},
        onEndDateSelected = {},
        onStartDateCancelClick = { /*TODO*/ },
        onEndDateCancelClick = { /*TODO*/ },
        onStartDateButtonClick = { /*TODO*/ },
        onEndDateButtonClick = { /*TODO*/ },
        onResetButtonClick = {}
    )
}