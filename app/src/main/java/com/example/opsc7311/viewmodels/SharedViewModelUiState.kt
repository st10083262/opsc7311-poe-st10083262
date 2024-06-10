package com.example.opsc7311.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.example.opsc7311.ui.models.Timesheet
import com.example.opsc7311.util.calculateDuration
import java.util.SortedMap
import kotlin.math.roundToInt

data class SharedViewModelUiState (
    val list: SnapshotStateList<Timesheet> = mutableStateListOf(),
    val filteredList: SnapshotStateList<Timesheet> = list.toMutableStateList(),
    val filteredCategories: SortedMap<String, Int> =
        filteredList
            .flatMap { myClass -> myClass.categories.map { string -> Pair(string, calculateDuration
        (myClass.startTime, myClass.endTime, hideDecimals = false)) } }
        .groupBy { it.first }
        .mapValues { entry -> entry.value.sumOf { it.second }.roundToInt() }
        .toSortedMap(),
    val startDate: String = "Start Date",
    val endDate: String = "End Date",
    val minDailyGoal: Int = 1,
    val maxDailyGoal: Int = 10,
)