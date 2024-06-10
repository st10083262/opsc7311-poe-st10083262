package com.example.opsc7311.ui.screens.goals

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.opsc7311.ui.screens.edit.EditScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.roundToInt

class GoalScreenViewModel : ViewModel() {

    // UI State
    private val _uiState = MutableStateFlow(GoalScreenUiState())
    val uiState: StateFlow<GoalScreenUiState> = _uiState.asStateFlow()

    fun updateMinDailyGoal()
    {
        if(_uiState.value.minDailyGoalSliderValue > _uiState.value.maxDailyGoalSliderValue)
        {
            _uiState.update { currentState ->
                currentState.copy(
                    minDailyGoal = _uiState.value.maxDailyGoal,
                    minDailyGoalSliderValue = _uiState.value.maxDailyGoalSliderValue
                )
            }
        }
        else {
            _uiState.update { currentState ->
                currentState.copy(
                    minDailyGoal = _uiState.value.minDailyGoalSliderValue.roundToInt()
                )
            }
        }
    }

    fun updateMaxDailyGoal()
    {
        _uiState.update { currentState ->
            currentState.copy(
                maxDailyGoal = _uiState.value.maxDailyGoalSliderValue.roundToInt(),
                minSliderRange = 0F.._uiState.value.maxDailyGoalSliderValue
            )
        }

        updateMinDailyGoal()
        updateHoursWorkedToday(_uiState.value.hoursWorkedToday)
    }

    fun setMinDailyGoalSliderValue(value: Float)
    {
        _uiState.update { currentState ->
            currentState.copy(
                minDailyGoalSliderValue = value
            )
        }
    }
    fun setMaxDailyGoalSliderValue(value: Float)
    {
        _uiState.update { currentState ->
            currentState.copy(
                maxDailyGoalSliderValue = value
            )
        }
    }

    fun updateHoursWorkedToday(value: Int)
    {
        _uiState.update { currentState ->
            currentState.copy(
                hoursWorkedToday = value,
                percentHoursWorkedToGoal = ((value / _uiState.value.maxDailyGoal.toFloat()) * 100),
                prompt = generateMessage(((value / _uiState.value.maxDailyGoal.toFloat()) * 100))
            )
        }
    }

    private fun generateMessage(value: Float): String {
        return when {
            value < 25f -> "Keep going!"
            value < 50f -> "You're doing great!"
            value < 75f -> "Almost there!"
            value < 100f -> "You're so close!"
            value < 150f -> "Congratulations!"
            else -> "Keep it up!"
        }
    }


}