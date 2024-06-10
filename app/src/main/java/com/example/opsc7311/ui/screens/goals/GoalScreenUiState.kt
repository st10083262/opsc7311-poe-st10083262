package com.example.opsc7311.ui.screens.goals

data class GoalScreenUiState(
    val minDailyGoalSliderValue: Float = 1F,
    val maxDailyGoalSliderValue: Float = 10F,
    val minDailyGoal: Int = 1,
    val maxDailyGoal: Int = 10,
    val minSliderRange: ClosedFloatingPointRange<Float> = 0F..maxDailyGoalSliderValue,
    val prompt: String = "Looking Good!",
    val hoursWorkedToday: Int = 3,
    val percentHoursWorkedToGoal: Float = ((hoursWorkedToday / maxDailyGoal.toFloat()) * 100),
)
