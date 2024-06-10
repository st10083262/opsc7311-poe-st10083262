package com.example.opsc7311.ui.models

import com.example.opsc7311.util.Converters
import java.util.Date

data class Timesheet(
    val id: String = "-1",
    val title: String = "Untitled",
    val date: String = Converters.dateToTextDisplay.format(Date()),
    val startTime: String = Converters.timeToDateDisplay.format(Date()),
    val endTime: String = Converters.timeToDateDisplay.format(Date()),
    val categories: MutableList<String> = mutableListOf(),
    val images: MutableList<Int> = mutableListOf()
)
