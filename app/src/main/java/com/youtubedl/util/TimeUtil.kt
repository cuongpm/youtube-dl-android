package com.youtubedl.util

/**
 * Created by cuongpm on 1/29/19.
 */

object TimeUtil {

    fun convertMilliSecondsToTimer(milliSeconds: Long): String {
        var hourString: String
        var secondString: String
        var minuteString: String

        val hours = (milliSeconds / (1000 * 60 * 60)).toInt()
        val minutes = (milliSeconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
        val seconds = (milliSeconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()

        hourString = if (hours > 0) "$hours" else ""
        minuteString = if (minutes < 10) "0$minutes" else "" + minutes
        secondString = if (seconds < 10) "0$seconds" else "" + seconds

        return "$hourString:$minuteString:$secondString"
    }
}