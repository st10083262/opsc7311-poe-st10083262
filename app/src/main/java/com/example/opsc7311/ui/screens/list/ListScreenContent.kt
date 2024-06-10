package com.example.opsc7311.ui.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.opsc7311.viewmodels.SharedViewModelUiState

@Composable
fun ListScreenContent(
    sharedViewModelUiState: SharedViewModelUiState,
    onTimesheetClicked: (String) -> Unit,
    onImageClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        items(
            items = sharedViewModelUiState.filteredList,
            key = { timesheet ->
                // Return a stable + unique key for the item
                timesheet.id
            }
        ) {timeSheet->

            TimesheetListItem(
                timeSheet = timeSheet,
                onTimesheetClicked = onTimesheetClicked,
                onImageClicked = onImageClicked
            )
        }
    }
}

@Preview
@Composable
fun ListScreenContentPreview()
{
    ListScreenContent(
        sharedViewModelUiState = SharedViewModelUiState(),
        onTimesheetClicked = {},
        onImageClicked = {}
    )
}