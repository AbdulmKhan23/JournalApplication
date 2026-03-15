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