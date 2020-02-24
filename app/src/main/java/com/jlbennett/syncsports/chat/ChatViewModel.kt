package com.jlbennett.syncsports.chat

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jlbennett.syncsports.util.MatchTime
import com.jlbennett.syncsports.util.State
import com.jlbennett.syncsports.util.User
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

class ChatViewModel(matchTime: MatchTime, roomName: String, user: User) : ViewModel() {

    //private val socket = IO.socket("http://192.168.122.1:4000")
    //private val socket = IO.socket("http://10.0.2.2:4000/")//change emulator proxy settings (settings/proxy)
    private val socket = IO.socket("http://syncsport.herokuapp.com/")
    private val room: String = roomName
    private val handler = Handler()
    private val timerRunnable: Runnable = run {
        Runnable {
            val localMatchTime = _matchTime.value!!
            val state = localMatchTime.state
            var minutes = localMatchTime.minutes
            var seconds = localMatchTime.seconds
            var quarterSeconds = localMatchTime.quarterSeconds

            if (quarterSeconds % 4 == 0) {
                Log.d("ChatViewModel log", "Updating time: $minutes:$seconds.${quarterSeconds * 25}")
                if (state == State.FIRST_HALF || state == State.SECOND_HALF) {
                    seconds = (seconds + 1) % 60
                    if (seconds == 59) {
                        minutes = (minutes + 1) % 60
                    }
                }//TODO handle state here
            }
            quarterSeconds = (quarterSeconds + 1) % 4
            _matchTime.postValue(MatchTime(state, minutes, seconds, quarterSeconds))
            handler.postDelayed(timerRunnable, 1000 / 4)
        }
    }


    val messages = listOf<ChatMessage>()


    private val _eventMessageToShow = MutableLiveData<Boolean>()
    val eventMessageToShow: LiveData<Boolean>
        get() = _eventMessageToShow

    private val _receivedMessage = MutableLiveData<ChatMessage>()
    val receivedMessage: LiveData<ChatMessage>
        get() = _receivedMessage

    private val _matchTime = MutableLiveData<MatchTime>()
    val matchTime: LiveData<MatchTime>
        get() = _matchTime

    private var _user = User("user", "#0B4AB0")

    init {
        _matchTime.value = matchTime
        _user = user
        handler.postDelayed(timerRunnable, 1000)
        _eventMessageToShow.value = false
        connectToChatAPI()
    }

    private fun connectToChatAPI() {
        Log.d("ChatNetworkLog", "connectToChatAPI Called")
        socket.on(Socket.EVENT_CONNECT) {
            socket.emit("room", room)
            socket.emit("username", _user.name)
            socket.emit("room", room)
        }

        socket.on("chat_message") { args ->
            //TODO cannot receive messages from WebApp. It doesn't send an appropriate MatchTime JSON object.
            //TODO maybe make the webapp receive messages from all rooms. Send to all.
            val msgObject = args[0] as JSONObject
            val username = msgObject.get("username") as String
            val userColor = msgObject.get("color") as String
            val message = msgObject.get("message") as String

            val timeObject = msgObject.get("user_time") as JSONObject
            val minutes = timeObject.get("minutes") as Int
            val seconds = timeObject.get("seconds") as Int
            val quarterSeconds = timeObject.get("quarter_seconds") as Int
            val state = when (timeObject.get("state")) {
                "PRE_MATCH" -> State.PRE_MATCH
                "FIRST_HALF" -> State.FIRST_HALF
                "HALF_TIME" -> State.HALF_TIME
                "SECOND_HALF" -> State.SECOND_HALF
                "FULL_TIME" -> State.FULL_TIME
                else -> State.PRE_MATCH
            }
            val incomingMatchTime = MatchTime(state, minutes, seconds, quarterSeconds)
            val chatMessage = ChatMessage((User(username, userColor)), message, incomingMatchTime)
            Log.d("ChatNetworkLog", "received: $incomingMatchTime")
            val timeDifference: Long = calculateDifferenceInMillis(incomingMatchTime)
            updateMessage(chatMessage, timeDifference)
        }
        socket.connect()
    }

    private fun calculateDifferenceInMillis(incomingMatchTime: MatchTime): Long {
        //TODO handle different 'states'
        val currentMatchTime = _matchTime.value!!
        var minuteDifference = 0
        if (incomingMatchTime.minutes > currentMatchTime.minutes) {
            minuteDifference = incomingMatchTime.minutes - currentMatchTime.minutes
        }
        var secondDifference = 60 * minuteDifference
        secondDifference += incomingMatchTime.seconds - currentMatchTime.seconds
        val millisecondDifference = (secondDifference * 1000L) +
                ((incomingMatchTime.quarterSeconds - currentMatchTime.quarterSeconds) * 250)
        return if (millisecondDifference >= 0) millisecondDifference else 0L

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
        msgObject.put("username", _user.name)
        msgObject.put("color", _user.color)
        msgObject.put("message", message)
        val timeObject = JSONObject()
        timeObject.put("state", _matchTime.value!!.state)
        timeObject.put("minutes", _matchTime.value!!.minutes)
        timeObject.put("seconds", _matchTime.value!!.seconds)
        timeObject.put("quarter_seconds", _matchTime.value!!.quarterSeconds)
        msgObject.put("user_time", timeObject)
        Log.d("ChatNetworkLog", "Before emission: ${_user.name} : $message : $timeObject")
        socket.emit("chat_message", msgObject)
    }

    fun disconnectFromChatroom() {
        socket.disconnect()
    }

    fun reconnectToChatroom() {
        if (!socket.connected()) {
            socket.connect()
        }
    }

    fun onDisplayMessageComplete() {
        _eventMessageToShow.value = false
    }

    fun resumeTimer(matchTime: MatchTime) {
        Log.d("chatLife", "resumeTimer - posting seconds:${matchTime.seconds}.")
        _matchTime.value = matchTime
        handler.postDelayed(timerRunnable, 1000)
    }

    fun pauseTimer() {
        Log.d("chatLife", "pauseTimer - removing callbacks.")
        handler.removeCallbacks(timerRunnable)
    }

    fun updateMatchTime(matchTime: MatchTime) {
        _matchTime.value = matchTime
    }
}