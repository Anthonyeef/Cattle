package io.github.anthonyeef.cattle.entity

/**
 * Photo entity within a [io.github.anthonyeef.cattle.data.statusData.Status] .
 */
data class Photo (
    var thumburl: String = "",

    var imageurl: String = "",

    var largeurl: String = ""
)