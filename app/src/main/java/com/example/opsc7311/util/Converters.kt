package com.example.opsc7311.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Locale

object Converters {


    /* The dates and time are in a Date() class so we need to convert
     * them to Strings.
     * Works as long as the user doesn't change local during run.
     */
    @SuppressLint("ConstantLocale")
    val localDateToDate = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.getDefault()
    )

    @SuppressLint("ConstantLocale")
    val timeToDateDisplay = SimpleDateFormat(
        "HH:mm",
        Locale.getDefault()
    )

    @SuppressLint("ConstantLocale")
    val dateToTextDisplay = SimpleDateFormat(
        "dd MMMM yyyy",
        Locale.getDefault()
    )
}