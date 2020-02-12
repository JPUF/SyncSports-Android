package com.jlbennett.syncsports.chat

import com.jlbennett.syncsports.util.MatchTime
import com.jlbennett.syncsports.util.User

data class ChatMessage (val user: User, val message: String, val matchTime: MatchTime)