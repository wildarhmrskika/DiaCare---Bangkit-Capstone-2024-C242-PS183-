package com.capstone.diacare.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun getYearFromISODate(isoDate: String): String {
    val formatter = DateTimeFormatter.ISO_DATE_TIME // For ISO 8601 format
    val dateTime = LocalDateTime.parse(isoDate, formatter)
    return dateTime.year.toString()
}