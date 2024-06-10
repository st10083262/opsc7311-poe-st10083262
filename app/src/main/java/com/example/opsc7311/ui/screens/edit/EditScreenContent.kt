package com.example.opsc7311.ui.screens.edit

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.opsc7311.util.DateCalPicker
import com.example.opsc7311.util.ImageRow
import com.example.opsc7311.util.TimeClockPicker
import java.time.LocalDate

@Composable
fun EditScreenContent(
    editScreenUiState: EditScreenUiState,
    onSelectDate: (LocalDate) -> Unit,
    onStartTimeSelected: (Int, Int) -> Unit,
    onEndTimeSelected: (Int, Int) -> Unit,
    onDateDisplayClick: () -> Unit,
    onStartTimeDisplayClick: () -> Unit,
    onEndTimeDisplayClick: () -> Unit,
    onCategoryViewClick: () -> Unit,
    onImageClicked: (Int) -> Unit,
    onDismissImagePopup: () -> Unit,
    newCategoryText: String,
    onNewCategoryTextValChanged: (String) -> Unit,
    onDismissAddCategoryPopup: () -> Unit,
    onConfirmAddCategoryClicked: () -> Unit,
    modifier: Modifier = Modifier

)
{
    // Calendar picker
    DateCalPicker(calendarState = editScreenUiState.calendarState,
        onSelectDate = onSelectDate,
        onNegativeClick = {}
    )

    // First time picker
    TimeClockPicker(
        clockState = editScreenUiState.clockState1,
        onTimeSelected = onStartTimeSelected
    )

    // Second time picker
    TimeClockPicker(
        clockState = editScreenUiState.clockState2,
        onTimeSelected = onEndTimeSelected
    )

    // Show the column of pickers and dates
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        DateDisplay(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            date = editScreenUiState.date,
            onIconClicked = onDateDisplayClick
        )
        TimeDisplay(
            title = "Start Time",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            time = editScreenUiState.startTime,
            onIconClicked = onStartTimeDisplayClick
        )
        TimeDisplay(
            title = "End Time",
            modifier = Modifier
                .fillMaxWidth(),
            time = editScreenUiState.endTime,
            onIconClicked = onEndTimeDisplayClick
        )

        DurationDisplay(
            modifier = Modifier
                .padding(top = 28.dp, bottom = 28.dp)
                .height(120.dp)
                .width(160.dp),
            duration = editScreenUiState.duration
        )

        CategoryView(
            modifier = Modifier,
            categories = editScreenUiState.categories,
            onCategoryClick = onCategoryViewClick
        )

        Gallery(
            images = editScreenUiState.images,
            onManageImagesClick = {},
            onImageClicked = onImageClicked
        )

        if (editScreenUiState.showImageDetailPopup) {
            ImagePopup(
                imageID = editScreenUiState.imageId,
                modifier = Modifier,
                onDismissRequested = onDismissImagePopup
            )
        }


        if (editScreenUiState.showEnterCategoryPopup) {
            AddCategoryPopup(
                text = newCategoryText,
                isUnique = editScreenUiState.isNewCategoryTextUnique,
                onValueChanged = onNewCategoryTextValChanged,
                onDismissRequested = onDismissAddCategoryPopup,
                onConfirmClicked = onConfirmAddCategoryClicked
            )
        }

    }
}


// From here on is the implementation of the composable
// used above.

@Composable
fun AddCategoryPopup(
    text: String,
    isUnique: Boolean,
    onValueChanged: (it: String) -> Unit,
    onDismissRequested: () -> Unit,
    onConfirmClicked: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismissRequested,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        title = { Text("Enter a category") },
        text = {
            TextField(
                value = text,
                onValueChange = onValueChanged,
                singleLine = true,
                label = { Text(text = "Name") },
                supportingText = {
                    if (!isUnique) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "There is already a category with this name!"
                        )
                    }
                }
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirmClicked,
                enabled = isUnique
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequested) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ImagePopup(
    @DrawableRes imageID: Int,
    modifier: Modifier = Modifier,
    onDismissRequested: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequested) {
        Column {
            Image(
                modifier = modifier,
                painter = painterResource(id = imageID),
                contentDescription = null,
                contentScale = ContentScale.Inside
            )
        }
    }
}

@Composable
fun Gallery(
    images: MutableList<Int>,
    onManageImagesClick: () -> Unit,
    onImageClicked: (imageID: Int) -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
            ) { onManageImagesClick() }
            .padding(top = 8.dp, bottom = 8.dp),
        text = "Images ᐳ",
        style = MaterialTheme.typography.labelLarge
    )

    ImageRow(
        images = images,
        onImageClicked = onImageClicked,
        modifier = Modifier.heightIn(max = 150.dp)
    )
}

@Composable
fun CategoryView(
    modifier: Modifier = Modifier,
    categories: MutableList<String>,
    onCategoryClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    enabled = true,
                ) { onCategoryClick.invoke() }
                .padding(top = 8.dp, bottom = 8.dp),
            text = "Categories ᐳ",
            style = MaterialTheme.typography.labelLarge
        )
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                ElevatedSuggestionChip(
                    onClick = { /*TODO*/ },
                    label = {
                        Text(text = category)
                    }
                )
            }

        }
    }
}


// Show the difference between the start and end time in a box.
@Composable
fun DurationDisplay(modifier: Modifier = Modifier, duration: String) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10),
        tonalElevation = 2.dp
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = duration,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Light
            )
            Text(
                text = "Hours Spent",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun DateDisplay(
    date: String,
    onIconClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Date",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            IconButton(onClick = onIconClicked) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }
        }
    }
}

@Composable
fun TimeDisplay(
    title: String,
    time: String,
    onIconClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = time,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            IconButton(onClick = onIconClicked) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EditScreenContentPreview() {
    EditScreenContent(
        editScreenUiState = EditScreenUiState(),
        onSelectDate = {},
        onStartTimeSelected = { _, _ ->},
        onEndTimeSelected = { _, _ ->},
        onDateDisplayClick = { /*TODO*/ },
        onStartTimeDisplayClick = { /*TODO*/ },
        onEndTimeDisplayClick = { /*TODO*/ },
        onCategoryViewClick = { /*TODO*/ },
        onImageClicked = {},
        onDismissImagePopup = { /*TODO*/ },
        newCategoryText = "category",
        onNewCategoryTextValChanged = {},
        onDismissAddCategoryPopup = { /*TODO*/ },
        onConfirmAddCategoryClicked = {}
    )
}