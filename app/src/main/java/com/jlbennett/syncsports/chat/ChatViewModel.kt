package com.jlbennett.syncsports.chat

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jlbennett.syncsports.util.User
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.util.*

class ChatViewModel : ViewModel() {

    private val socket = IO.socket("http://192.168.122.1:4000")

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

    private val _eventMessageToShow = MutableLiveData<Boolean>()
    val eventMessageToShow: LiveData<Boolean>
        get() = _eventMessageToShow

    init {
        Log.d("viewmodel", "ChatViewModel init")
        _eventMessageToShow.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("viewmodel", "ChatViewModel onCleared()")
    }

    fun connectToChatAPI() {
        Log.d("ChatNetworkLog", "connectToChatAPI Called")
        socket.on(Socket.EVENT_CONNECT) {
            val usernameString = "AndroidApp"
            socket.emit("username", usernameString)
        }


        socket.on("chat_message") { args ->
            val msgObject = args[0] as JSONObject
            val username: String = msgObject.get("username") as String
            val usercolor: String = msgObject.get("color") as String
            val message: String = msgObject.get("message") as String
            Log.d("usercolour", "$username - $usercolor - $message")
            //displayMessage(User(username, Color.parseColor(usercolor)), message)
            _eventMessageToShow.value = true
            //TODO send message with this.
        }
        socket.connect()
    }

    fun sendMessage(message: String) {
        val msgObject = JSONObject()
        msgObject.put("username", "AndroidApp")
        msgObject.put("color", "#5e0104")
        msgObject.put("message", message)
        msgObject.put("user_time", Date().time)
        socket.emit("chat_message", msgObject)
    }

    fun onDisplayMessageComplete() {
        _eventMessageToShow.value = false
    }
}