package com.khan.journalapplication.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatdate(time: Long): String{
    val date= Date(time)
    val format=SimpleDateFormat("EEE, d MMM hh:mm aaa",
        Locale.getDefault())
    return format.format(date)
}

fun greeting(name: String, time: Long = System.currentTimeMillis()): String {
    val hour = java.util.Calendar.getInstance().apply {
        timeInMillis = time
    }.get(java.util.Calendar.HOUR_OF_DAY)

    val greeting = when (hour) {
        in 5..11 -> "Good morning"
        in 12..16 -> "Good afternoon"
        else -> "Good evening"
    }

    return "$greeting,\n$name"
}