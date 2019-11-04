package com.jlbennett.syncsports.chat

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModel
import com.jlbennett.syncsports.util.User

//TODO follow from tut: step 10 onwards. To populate this VM. 

class ChatViewModel : ViewModel() {

    val dummyMessages = listOf(
        ChatMessage(
            User("AstroHound", Color.parseColor("#EE0505")),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent semper varius sem, ac aliquet neque volutpat in. Maecenas magna tellus, viverra egestas gravida id, pulvinar eu velit. Quisque gravida risus nec eros ullamcorper, vitae tincidunt justo convallis. Praesent consectetur sollicitudin feugiat. Fusce id massa vel metus ultricies sodales sed in arcu."
        ),
        ChatMessage(
            User("EightSevenFortyFifteen", Color.parseColor("#05EE05")),
            "Lorem ipsum dolor sit amet"
        ),
        ChatMessage(
            User("-RainMan500", Color.parseColor("#0505EE")),
            "Ut lacus purus, suscipit eget purus non, elementum laoreet turpis. Aliquam sit amet tincidunt dolor, eu aliquet mi. Mauris congue eu est non auctor. Ut finibus arcu augue, id vulputate est malesuada sed. Sed non felis maximus, dapibus ante quis, egestas purus. Sed lacinia est magna, a aliquam augue euismod ac. Nullam sed est risus. In blandit maximus eros vitae tincidunt."
        ),
        ChatMessage(
            User("SharmaGurthX", Color.parseColor("#05EEEE")),
            "Lorem ipsum dolor sit amet"
        )
    )

    init {
        Log.d("viewmodel", "ChatViewModel init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("viewmodel", "ChatViewModel onCleared()")
    }
}