package io.github.anthonyeef.cattle.utils

import org.ocpsoft.prettytime.PrettyTime
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 */
object TimeUtils {
    val prettyTime = PrettyTime()
    val formatter = SimpleDateFormat(
            "EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)

    fun prettyFormat(date: Date): String {
        return prettyTime.format(date)
                .replace("minutes", "m")
                .replace("minute", "m")
                .replace("moments ago", "now")
    }

    fun prettyFormat(date: String): String {
        return prettyFormat(formatter.parse(date))
    }

    fun getTime(date: String): String {
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(formatter.parse(date))
    }

    fun getDate(date: String): String {
        return DateFormat.getDateInstance(DateFormat.SHORT).format(formatter.parse(date))
    }
}