package com.jlbennett.syncsports.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jlbennett.syncsports.util.MatchTime
import com.jlbennett.syncsports.util.User

/**
 * A class to create a ChatViewModel with the given parameters.
 *
 * This is used to return the singleton ViewModel class from within the ChatFragment class.
 */
class ChatViewModelFactory(
    private val matchTime: MatchTime,
    private val roomName: String,
    private val user: User
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(matchTime, roomName, user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}