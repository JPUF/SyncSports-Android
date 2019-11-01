package com.jlbennett.syncsports.chat

import com.jlbennett.syncsports.util.User

data class ChatMessage (val user: User, val message: String)

//TODO should also include MatchTime (point in the match where the message was sent).