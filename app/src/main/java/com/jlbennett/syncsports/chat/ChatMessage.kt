package com.jlbennett.syncsports.chat

import com.jlbennett.syncsports.util.MatchTime
import com.jlbennett.syncsports.util.User

/**
 * A Plain-Old-Data class to represent a single message.
 */
data class ChatMessage(val id: Int?, val parentID: Int, val user: User, val message: String, val matchTime: MatchTime)