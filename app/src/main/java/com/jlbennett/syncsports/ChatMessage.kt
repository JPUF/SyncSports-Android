package com.jlbennett.syncsports

data class ChatMessage (val user: User, val message: String)

//TODO should also include MatchTime (point in the match where the message was sent).