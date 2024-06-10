package com.example.opsc7311.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.opsc7311.ui.models.Timesheet
import com.example.opsc7311.util.Converters
import com.example.opsc7311.util.calculateDuration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
import kotlin.math.roundToInt


class SharedViewModel : ViewModel() {

    // UI State
    private val _uiState = MutableStateFlow(SharedViewModelUiState())
    val uiState: StateFlow<SharedViewModelUiState> = _uiState.asStateFlow()

    init {
        val db = Firebase.firestore
        db.collection("timesheets").get()
            .addOnSuccessListener { querySnapshot ->
                // Handle the success case
                val userList = mutableListOf<Timesheet>()
                for (document in querySnapshot.documents) {
                    val user = document.toObject(Timesheet::class.java)
                    Log.d("aaaaa", user.toString())
                    user?.let { addTimesheet(it) }
                }
                Log.d("aaaaa-full-list", userList.toString())
            }
            .addOnFailureListener { exception ->
                // Handle the failure case
            }

    }

    fun setStartDate(startDate: String)
    {
        _uiState.update { currentState ->
            currentState.copy(startDate = startDate)
        }
        filterList()
    }

    fun setEndDate(endDate: String)
    {
        _uiState.update { currentState ->
            currentState.copy(endDate = endDate)
        }
        filterList()
    }
    private fun filterList() {

        var date1: Date
        var date2: Date
        try {
            date1 = Converters.localDateToDate.parse(_uiState.value.startDate) as Date
            date2 = Converters.localDateToDate.parse(_uiState.value.endDate) as Date
        } catch (e: Exception) {
            date1 = Date(0)
            date2 = Date()
        }

        val sortedTimesheetList: MutableList<Timesheet> = mutableListOf()
        val catMap = mutableMapOf<String, Double>()

        for (x in _uiState.value.list) {
            val date = Converters.dateToTextDisplay.parse(x.date) as Date
            if (date in date1 .. date2) {
                sortedTimesheetList.add(x)

                // Get the sorted categories at the same time
                for (c in x.categories) {
                    catMap[c] = (catMap[c] ?: 0).toDouble() +
                            calculateDuration(
                                x.startTime, x.endTime, hideDecimals = false
                            )
                }
                //
            }
        }

        _uiState.value.filteredList.clear()
        _uiState.value.filteredList.addAll(sortedTimesheetList)

        //

        val filteredCategories = catMap
            .mapValues { (_, value) -> value.roundToInt() }
            .toSortedMap()

        _uiState.update { currentState ->
            currentState.copy(filteredCategories = filteredCategories)
        }

        //

    }

    fun resetFilter()
    {
        _uiState.value.filteredList.clear()
        _uiState.value.filteredList.addAll(_uiState.value.list)

        // Reset Categories
        val result = _uiState.value.filteredList
            .flatMap { myClass -> myClass.categories.map { string -> Pair(string, calculateDuration
                (myClass.startTime, myClass.endTime, hideDecimals = false)) } }
            .groupBy { it.first }
            .mapValues { entry -> entry.value.sumOf { it.second }.roundToInt() }
            .toSortedMap()

        _uiState.update { currentState ->
            currentState.copy(
                startDate = "Start Date",
                endDate = "End Date",
                filteredCategories = result
            )
        }
    }

    fun addTimesheet(timesheet: Timesheet) {
        _uiState.value.list.add(timesheet)
        filterList()
    }

    fun editTimesheet(timesheet: Timesheet) {
        val i = _uiState.value.list.indexOfFirst { it.id == timesheet.id }
        if(i >= 0)
        {
            _uiState.value.list[i] = timesheet
            filterList()
        }

    }

    fun deleteTimesheet(id: String) {
        _uiState.value.list.removeIf { it.id == id }
        filterList()
    }

    fun getTimeSheetOrReturnNew(id: String): Timesheet
    {
        var timesheet = _uiState.value.list.find { it.id == id }
        if(timesheet == null)
        {
            timesheet = Timesheet()
        }

        return timesheet
    }

    fun totalHoursSpentToday() : Double
    {

        var totalTimeSpent = 0.0

        for(timesheet in _uiState.value.list)
        {
            if(timesheet.date == Converters.dateToTextDisplay.format(Date()))
            {
                totalTimeSpent += calculateDuration(timesheet.startTime, timesheet.endTime, false)
            }
        }
        return totalTimeSpent
    }

    fun updateMinDailyGoal(value: Int)
    {

        _uiState.update { currentState ->
            currentState.copy(
                minDailyGoal = value
            )
        }
    }

    fun updateMaxDailyGoal(value: Int)
    {
        _uiState.update { currentState ->
            currentState.copy(
                maxDailyGoal = value
            )
        }
    }


    fun totalHoursSpentOnDate(date: String): Double {
        var totalTimeSpent = 0.0

        for (timesheet in _uiState.value.filteredList) {
            if (timesheet.date == date) {
                totalTimeSpent += calculateDuration(timesheet.startTime, timesheet.endTime, false)
            }
        }

        return totalTimeSpent
    }

    fun calculateTotalDurations(): Map<String, Double> {
        val totalDurations = mutableMapOf<String, Double>()

        for (data in _uiState.value.filteredList) {
            val duration = calculateDuration(data.startTime, data.endTime, false)

            // Check if the date already exists.
            if (totalDurations.containsKey(data.date)) {
                // Add the duration to the existing total duration
                val existingDuration = totalDurations[data.date]!!
                totalDurations[data.date] = existingDuration + duration
            } else {
                // Create a new total duration for the date
                totalDurations[data.date] = calculateDuration(data.startTime, data.endTime, false)
            }
        }

        return totalDurations
    }

}