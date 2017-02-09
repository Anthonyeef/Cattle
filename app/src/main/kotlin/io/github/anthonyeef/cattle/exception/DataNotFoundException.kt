package io.github.anthonyeef.cattle.exception

/**
 *
 */
class DataNotFoundException : Exception {
    var errorMessage: String = ""

    constructor() : super()
    constructor(s: String) : super(s) {
        errorMessage = s
    }

    override fun toString(): String {
        return errorMessage
    }
}