package io.github.anthonyeef.cattle.utils

import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 */
object TimeUtils {
    val prettyTime = PrettyTime()
    val formatter = SimpleDateFormat(
            "EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)

    fun format(date: Date): String {
        return prettyTime.format(date)
                .replace("minutes", "m")
                .replace("minute", "m")
                .replace("moments ago", "now")
    }

    fun format(date: String): String {
        return format(formatter.parse(date))
    }
}