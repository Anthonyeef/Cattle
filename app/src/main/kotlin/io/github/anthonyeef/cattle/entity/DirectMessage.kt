package io.github.anthonyeef.cattle.entity

/**
 *
 */
open class DirectMessage {
    open var id: String = ""
    open var text: String = ""
    open var senderId: String = ""
    open var recipientId: String = ""
    open var createdAt: String = ""
    open var senderScreenName: String = ""
    open var recipientScreenName: String = ""
    open var sender: UserInfo? = null
    open var inReplyTo: DirectMessage? = null
}