package com.jlbennett.syncsports.chat

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.Handler
import com.jlbennett.syncsports.util.MatchTime
import com.jlbennett.syncsports.util.State
import com.jlbennett.syncsports.util.User
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.util.*

class ChatViewModel(matchTime: MatchTime, username: String) : ViewModel() {

    //private val socket = IO.socket("http://192.168.122.1:4000")
    //private val socket = IO.socket("http://10.0.2.2:4000/")//change emulator proxy settings (settings/proxy)
    private val socket = IO.socket("https://syncsport.herokuapp.com/")

    //TODO provide a method to remove callbacks from handler. Call from Fragment onPause.
    private val handler = Handler()
    private val timerRunnable: Runnable = run {
        Runnable {
            val state = _matchTime.value!!.state
            var minutes = _matchTime.value!!.minutes
            var seconds = _matchTime.value!!.seconds
            Log.d("Match Time", "Updating time: $seconds")
            if (state == State.FIRST_HALF || state == State.SECOND_HALF) {
                if (seconds < 59) {
                    seconds += 1
                } else {
                    minutes += 1
                    seconds = 0
                }
            }
            _matchTime.postValue(MatchTime(state, minutes, seconds))
            handler.postDelayed(timerRunnable, 1000)
        }
    }

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

    private val _receivedMessage = MutableLiveData<ChatMessage>()
    val receivedMessage: LiveData<ChatMessage>
        get() = _receivedMessage

    private val _matchTime = MutableLiveData<MatchTime>()
    val matchTime: LiveData<MatchTime>
        get() = _matchTime

    private val _username = MutableLiveData<String>()

    init {
        _matchTime.value = matchTime
        _username.value = username
        _eventMessageToShow.value = false
        connectToChatAPI()
        handler.postDelayed(timerRunnable, 1000)
    }

    private fun connectToChatAPI() {
        Log.d("ChatNetworkLog", "connectToChatAPI Called")
        socket.on(Socket.EVENT_CONNECT) {
            val usernameString = _username.value
            socket.emit("username", usernameString)
        }

        socket.on("chat_message") { args ->
            val msgObject = args[0] as JSONObject
            val username: String = msgObject.get("username") as String
            val usercolor: String = msgObject.get("color") as String
            val message: String = msgObject.get("message") as String
            val chatMessage = ChatMessage((User(username, Color.parseColor(usercolor))), message)
            _receivedMessage.postValue(chatMessage)
            _eventMessageToShow.postValue(true)
        }
        socket.connect()
    }

    fun sendMessage(message: String) {
        val msgObject = JSONObject()
        msgObject.put("username", _username.value)
        msgObject.put("color", "#5e0104")
        msgObject.put("message", message)
        msgObject.put("user_time", Date().time)
        Log.d("ChatNetworkLog", "Before emission: ${_username.value} : $message")
        socket.emit("chat_message", msgObject)
    }

    fun onDisplayMessageComplete() {
        _eventMessageToShow.value = false
    }

    fun stopUpdatingTimer() {
        handler.removeCallbacks(timerRunnable)
    }
}