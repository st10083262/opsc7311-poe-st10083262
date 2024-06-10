package com.example.opsc7311.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.opsc7311.ui.models.Timesheet
import com.example.opsc7311.util.ImageRow

@Composable
fun TimesheetListItem(
    timeSheet: Timesheet,
    onTimesheetClicked: (String) -> Unit,
    onImageClicked: (Int) -> Unit
) {
    Card(
        modifier = Modifier.clickable { onTimesheetClicked(timeSheet.id) }
    ) {
        Column(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            Text(
                text = timeSheet.title,
                style = MaterialTheme.typography.headlineMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = timeSheet.date)
                Text(text = "${timeSheet.startTime} - ${timeSheet.endTime}")
            }
            LazyRow(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(timeSheet.categories) { category ->
                    ElevatedSuggestionChip(
                        onClick = { /*TODO*/ },
                        label = {
                            Text(text = category)
                        }
                    )
                }

            }
            ImageRow(
                modifier = Modifier.heightIn(max = 100.dp).padding(top = 4.dp),
                images = timeSheet.images,
                onImageClicked = onImageClicked
            )
        }
    }
}

@Preview
@Composable
fun ListItemPreview()
{
    TimesheetListItem(
        timeSheet = Timesheet(),
        onTimesheetClicked = {},
        onImageClicked = {}
    )
}