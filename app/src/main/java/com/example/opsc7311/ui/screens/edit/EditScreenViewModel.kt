package com.example.opsc7311.ui.screens.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.opsc7311.ui.models.Timesheet
import com.example.opsc7311.util.Converters
import com.example.opsc7311.util.calculateDuration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date

class EditScreenViewModel(timesheet: Timesheet) : ViewModel() {

    // UI State
    private val _uiState = MutableStateFlow(EditScreenUiState())
    val uiState: StateFlow<EditScreenUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                id = timesheet.id,
                title = timesheet.title,
                date = timesheet.date,
                startTime = timesheet.startTime,
                endTime = timesheet.endTime,
                duration = calculateDuration(
                    startTime = timesheet.startTime,
                    endTime = timesheet.endTime
                ),
                categories = timesheet.categories,
                images = timesheet.images
            )
        }
    }
    fun updateDate(date: String){
        val dateObject: Date = Converters.localDateToDate.parse(date) as Date
        val newDate: String = Converters.dateToTextDisplay.format(dateObject)
        _uiState.update { currentState ->
            currentState.copy(date = newDate)
        }
    }

    fun updateStartTime(hours: Int, minutes: Int){
        val startTime = hours.toString().padStart(2, '0') +
                ":" +
                minutes.toString().padStart(2, '0')

        val duration: String = calculateDuration(
            startTime = startTime,
            _uiState.value.endTime
        )

        _uiState.update { currentState ->
            currentState.copy(startTime = startTime, duration = duration)
        }
    }

    fun showClock1()
    {
        _uiState.value.clockState1.show()
    }

    fun showClock2()
    {
        _uiState.value.clockState2.show()
    }

    fun showCalendar()
    {
        _uiState.value.calendarState.show()
    }

    fun showEnterCategoryPopup(value: Boolean)
    {
        _uiState.update { currentState ->
            currentState.copy(showEnterCategoryPopup = value)
        }
    }

    fun showImageDetailPopup(value: Boolean)
    {
        _uiState.update { currentState ->
            currentState.copy(showImageDetailPopup = value)
        }
    }

    fun setImageToShow(imageId: Int)
    {
        _uiState.update { currentState ->
            currentState.copy(imageId = imageId)
        }
    }

    var newCategoryText by mutableStateOf("")
        private set


    private fun setIsAddCategoryTextUnique(value: Boolean)
    {
        _uiState.update { currentState ->
            currentState.copy(isNewCategoryTextUnique = value)
        }
    }
    fun updateAddCategoryText(it: String)
    {
        newCategoryText = it
        if(_uiState.value.categories.contains(newCategoryText))
        {
            setIsAddCategoryTextUnique(false)
        } else {
            setIsAddCategoryTextUnique(true)
        }
    }

    fun addCategory()
    {
        if(!_uiState.value.categories.contains(newCategoryText))
        {
            val categories = _uiState.value.categories
            categories.add(newCategoryText)
            _uiState.update { currentState ->
                currentState.copy(categories = categories)
            }
        } else {
            setIsAddCategoryTextUnique(false)
        }
    }

    fun updateEndTime(hours: Int, minutes: Int){
        val endTime = hours.toString().padStart(2, '0') +
                ":" +
                minutes.toString().padStart(2, '0')

        val duration: String = calculateDuration(
            startTime = _uiState.value.startTime,
            endTime = endTime
        )

        _uiState.update { currentState ->
            currentState.copy(endTime = endTime, duration = duration)
        }
    }

    fun updateTitle(text: String)
    {
        _uiState.update { currentState ->
            currentState.copy(title = text)
        }
    }
}