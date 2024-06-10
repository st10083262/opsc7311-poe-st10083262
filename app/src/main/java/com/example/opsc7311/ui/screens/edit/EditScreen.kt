package com.example.opsc7311.ui.screens.edit

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.opsc7311.ui.models.Timesheet
import com.example.opsc7311.ui.screens.edit.appbar.EditBar
import com.example.opsc7311.viewmodels.SharedViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun EditScreen(
    editScreenViewModel: EditScreenViewModel,
) {

    val timesheetUiState by editScreenViewModel.uiState.collectAsState()

    EditScreenContent(
        editScreenUiState = timesheetUiState,
        onSelectDate = { date ->
            editScreenViewModel.updateDate(date.toString())
        },
        onStartTimeSelected = { hours, minutes ->
            editScreenViewModel.updateStartTime(hours = hours, minutes = minutes)
        },
        onEndTimeSelected = { hours, minutes ->
            editScreenViewModel.updateEndTime(hours = hours, minutes = minutes)
        },
        onDateDisplayClick = {
            editScreenViewModel.showCalendar()
        },
        onStartTimeDisplayClick = {
            editScreenViewModel.showClock1()
        },
        onEndTimeDisplayClick = {
            editScreenViewModel.showClock2()
        },
        onCategoryViewClick = {
            editScreenViewModel.showEnterCategoryPopup(true)
        },
        onImageClicked = { imageID: Int ->
            editScreenViewModel.showImageDetailPopup(true)
            editScreenViewModel.setImageToShow(imageID)
        },
        onDismissImagePopup = {
            editScreenViewModel.showImageDetailPopup(false)
        },
        newCategoryText = editScreenViewModel.newCategoryText,
        onNewCategoryTextValChanged = {
            editScreenViewModel.updateAddCategoryText(it)
        },
        onDismissAddCategoryPopup = {
            editScreenViewModel.updateAddCategoryText("")
            editScreenViewModel.showEnterCategoryPopup(false)
        },
        onConfirmAddCategoryClicked = {
            editScreenViewModel.addCategory()
            editScreenViewModel.updateAddCategoryText("")
            editScreenViewModel.showEnterCategoryPopup(false)
        }
    )

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TimesheetEditScreen(
    sharedViewModel: SharedViewModel,
    editScreenViewModel: EditScreenViewModel,
    onBackPressed: () -> Unit,
    afterSavePressed: () -> Unit
) {

    val ctx = LocalContext.current
    Scaffold(
        topBar = {
            EditBar(
                editScreenViewModel = editScreenViewModel,
                onBackPressed = onBackPressed,
                onSavePressed = {
                    if (editScreenViewModel.uiState.value.id == "-1") {

                        val db = Firebase.firestore
                        val id = db.collection("timesheets").document()


                        val t = Timesheet(
                            id = id.id,
                            title = editScreenViewModel.uiState.value.title,
                            date = editScreenViewModel.uiState.value.date,
                            startTime = editScreenViewModel.uiState.value.startTime,
                            endTime = editScreenViewModel.uiState.value.endTime,
                            categories = editScreenViewModel.uiState.value.categories,
                            images = editScreenViewModel.uiState.value.images
                        )

                        db.collection("timesheets")
                            .document(t.id).set(t) .addOnSuccessListener {documentReference ->
                                Toast.makeText(ctx, "Added Timesheet", Toast.LENGTH_LONG).show()
                                sharedViewModel.addTimesheet(t)
                            }.addOnFailureListener {
                                Toast.makeText(ctx, "There was a problem", Toast.LENGTH_LONG).show()

                            }


                    } else { // Update instead on adding
                        val t: Timesheet =Timesheet(
                            id = editScreenViewModel.uiState.value.id,
                            title = editScreenViewModel.uiState.value.title,
                            date = editScreenViewModel.uiState.value.date,
                            startTime = editScreenViewModel.uiState.value.startTime,
                            endTime = editScreenViewModel.uiState.value.endTime,
                            categories = editScreenViewModel.uiState.value.categories,
                            images = editScreenViewModel.uiState.value.images
                        )

                        val db = Firebase.firestore
                        db.collection("timesheets")
                            .document(t.id).set(t)

                        sharedViewModel.editTimesheet(timesheet = t)
                    }
                    afterSavePressed()
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            EditScreen(
                editScreenViewModel = editScreenViewModel
            )
        }
    }
}