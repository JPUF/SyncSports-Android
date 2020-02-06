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

class ChatViewModel(matchTime: MatchTime, username: String) : ViewModel() {

    //private val socket = IO.socket("http://192.168.122.1:4000")
    //private val socket = IO.socket("http://10.0.2.2:4000/")//change emulator proxy settings (settings/proxy)
    private val socket = IO.socket("https://syncsport.herokuapp.com/")
    private val room = "room1"//TODO set this during constructor. Dependent on which room the user chooses.

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
            //TODO include milliseconds (maybe 1/4 seconds?). So it's higher fidelity.
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
        handler.postDelayed(timerRunnable, 1000)
        _eventMessageToShow.value = false
        connectToChatAPI()
    }

    private fun connectToChatAPI() {
        Log.d("ChatNetworkLog", "connectToChatAPI Called")
        socket.on(Socket.EVENT_CONNECT) {
            socket.emit("room", room)
            socket.emit("username", _username.value)
            socket.emit("room", room)
        }

        socket.on("chat_message") { args ->
            //TODO cannot receive messages from WebApp. It doesn't send an appropriate MatchTime JSON object.
            val msgObject = args[0] as JSONObject
            val username = msgObject.get("username") as String
            val userColor = msgObject.get("color") as String
            val message = msgObject.get("message") as String
            val chatMessage = ChatMessage((User(username, Color.parseColor(userColor))), message)
            val timeObject = msgObject.get("user_time") as JSONObject
            val minutes = timeObject.get("minutes") as Int
            val seconds = timeObject.get("seconds") as Int
            val state = when(timeObject.get("state")) {
                "PRE_MATCH" -> State.PRE_MATCH
                "FIRST_HALF" -> State.FIRST_HALF
                "HALF_TIME" -> State.HALF_TIME
                "SECOND_HALF" -> State.SECOND_HALF
                "FULL_TIME" -> State.FULL_TIME
                else -> State.PRE_MATCH
            }
            val incomingMatchTime = MatchTime(state, minutes, seconds)
            Log.d("ChatNetworkLog", "received: $incomingMatchTime")
            val timeDifference: Long = calculateDifference(incomingMatchTime) * 1000L
            updateMessage(chatMessage, timeDifference)
        }
        socket.connect()
    }

    private fun calculateDifference(incomingMatchTime: MatchTime): Int {
        //TODO handle different 'states'
        val currentMatchTime = _matchTime.value!!
        var minuteDifference = 0
        if(incomingMatchTime.minutes > currentMatchTime.minutes) {
           minuteDifference = incomingMatchTime.minutes - currentMatchTime.minutes
        }
        var secondDifference = 60 * minuteDifference
        secondDifference += incomingMatchTime.seconds - currentMatchTime.seconds
        return if(secondDifference >= 0) secondDifference else 0

    }

    private fun updateMessage(chatMessage: ChatMessage, delay: Long) {
        class MessageDelayRunnable : Runnable {
            override fun run() {
                _receivedMessage.postValue(chatMessage)
                _eventMessageToShow.postValue(true)
            }
        }
        handler.postDelayed(MessageDelayRunnable(), delay)
        //TODO consider if I need to remove callbacks?
    }


    fun sendMessage(message: String) {
        val msgObject = JSONObject()
        msgObject.put("username", _username.value)
        msgObject.put("color", "#5e0104")//TODO take user input for color
        msgObject.put("message", message)
        val timeObject = JSONObject()
        timeObject.put("state", _matchTime.value!!.state)
        timeObject.put("minutes", _matchTime.value!!.minutes)
        timeObject.put("seconds", _matchTime.value!!.seconds)
        msgObject.put("user_time", timeObject)
        Log.d("ChatNetworkLog", "Before emission: ${_username.value} : $message : $timeObject")
        socket.emit("chat_message", msgObject)
    }

    fun disconnectFromChatroom() {
        socket.disconnect()
    }

    fun reconnectToChatroom() {
        if(!socket.connected()){
            socket.connect()
        }
    }

    fun onDisplayMessageComplete() {
        _eventMessageToShow.value = false
    }

    fun stopUpdatingTimer() {
        handler.removeCallbacks(timerRunnable)
    }
}