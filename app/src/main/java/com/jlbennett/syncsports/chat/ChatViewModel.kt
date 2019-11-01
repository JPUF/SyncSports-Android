package com.jlbennett.syncsports.chat

import android.util.Log
import androidx.lifecycle.ViewModel

//TODO follow from tut: step 10 onwards. To populate this VM. 

class ChatViewModel : ViewModel() {

    init {
        Log.d("viewmodel", "ChatViewModel init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("viewmodel", "ChatViewModel onCleared()")
    }
}