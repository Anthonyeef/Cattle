package io.github.anthonyeef.cattle.exception

/**
 * NetworkException will be thrown when met network problem.
 */
class NetworkException : Exception {
    constructor(): super()
    constructor(message: String): super(message)
    constructor(e: Throwable): super(e.message.toString())

    override fun toString(): String {
        return StringBuilder().append(" msg:").append(message).toString()
    }
}