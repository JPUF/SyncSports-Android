package com.jlbennett.syncsports.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jlbennett.syncsports.util.MatchTime

class ChatViewModelFactory(private val matchTime: MatchTime, private val username: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(matchTime, username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}