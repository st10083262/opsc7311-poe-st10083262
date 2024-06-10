package com.example.opsc7311.ui.screens.list.appbar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FilterBarViewModel: ViewModel() {
    // UI State
    private val _uiState = MutableStateFlow(FilterBarUiState())
    val uiState: StateFlow<FilterBarUiState> = _uiState.asStateFlow()

    fun updateStartDate(date: String){
        _uiState.update { currentState ->
            currentState.copy(startDate = date)
        }
    }

    fun updateEndDate(date: String){
        _uiState.update { currentState ->
            currentState.copy(endDate = date)
        }
    }

    fun setStartDateFilterExpanded(value: Boolean)
    {
        _uiState.update { currentState ->
            currentState.copy(startDateFilterExpanded = value)
        }
    }

    fun setEndDateFilterExpanded(value: Boolean)
    {
        _uiState.update { currentState ->
            currentState.copy(endDateFilterExpanded = value)
        }
    }

    fun setShowStartDatePicker(value: Boolean)
    {
        if(value)
        {
            _uiState.value.startDatePickerState.show()
        } else {
            _uiState.value.startDatePickerState.hide()
        }
    }

    fun setShowEndDatePicker(value: Boolean)
    {
        if(value)
        {
            _uiState.value.endDatePickerState.show()
        } else {
            _uiState.value.endDatePickerState.hide()
        }
    }

    fun reset()
    {
        _uiState.update {
            FilterBarUiState()
        }
    }
}