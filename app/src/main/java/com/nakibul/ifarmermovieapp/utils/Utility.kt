package com.nakibul.ifarmermovieapp.utils

object Utility {
    fun convertMinutesToHourMin(time: String): String {
        val totalMinutes = time.toIntOrNull() ?: return ""
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60
        return "${hours}h ${minutes}m"
    }
}