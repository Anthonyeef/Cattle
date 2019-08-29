package io.github.anthonyeef.cattle.entity

import androidx.room.Ignore

/**
 * Photo entity within a [io.github.anthonyeef.cattle.data.statusData.Status] .
 */
data class Photo (
    var thumburl: String = "",

    var imageurl: String = "",

    var largeurl: String = ""
) {
    @Ignore constructor(): this("", "", "")
}