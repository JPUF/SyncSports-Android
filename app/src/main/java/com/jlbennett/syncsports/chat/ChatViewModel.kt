package com.jlbennett.syncsports.chat

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jlbennett.syncsports.R
import com.jlbennett.syncsports.util.MatchTime
import com.jlbennett.syncsports.util.State
import com.jlbennett.syncsports.util.User
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

/**
 * The ViewModel for the ChatFragment. This handles all the dynamic data shown in the ChatFragment.
 *
 * This class's responsibilities include handling the time. Sending/Receiving messages, and the delay algorithm.
 *
 * @param matchTime The initial match time, on the ViewModel construction.
 */
class ChatViewModel(matchTime: MatchTime, roomName: String, user: User) : ViewModel() {

    /**
     * Reference to the chat namespace on the server.
     */
    private val chatSocket = IO.socket("http://syncsport.herokuapp.com/chat")
    private val room: String = roomName

    /**
     * The Android Handler that coordinates execution of the timer updating.
     */
    private val handler = Handler()

    /**
     * A runnable object to be executed via the Handler. It updates the 'clock'.
     *
     * It is updated every 1/4 second. This gives a theoretical message timing accuracy of +/-0.25s.
     */
    private val timerRunnable: Runnable = run {
        Runnable {
            val localMatchTime = _matchTime.value!!
            val state = localMatchTime.state
            var minutes = localMatchTime.minutes
            var seconds = localMatchTime.seconds
            var quarterSeconds = localMatchTime.quarterSeconds

            //Update the second and minute count only every four quarter seconds (once a second).
            if (quarterSeconds % 4 == 0) {
                if (state == State.FIRST_HALF || state == State.SECOND_HALF) {
                    seconds = (seconds + 1) % 60
                    if (seconds == 0) {
                        minutes = (minutes + 1) % 60
                    }
                }//TODO handle state here
            }

            //Update the quarter second count every time this runnable is called.
            quarterSeconds = (quarterSeconds + 1) % 4

            //Update the private LiveData with the new time. This triggers an event that is observed by the Fragment.
            _matchTime.postValue(MatchTime(state, minutes, seconds, quarterSeconds))

            //Recursively call the same runnable. With a delay of 250ms (quarter of a second).
            handler.postDelayed(timerRunnable, 1000 / 4)
        }
    }

    /**
     * The current messages.
     */
    val messages = listOf<ChatMessage>()

    /**
     * A private and mutable LiveData field, it represents whether or not there is a new message to be shown in the UI.
     */
    private val _eventMessageToShow = MutableLiveData<Boolean>()
    /**
     * The public LiveData which exposes the privately Mutable field. Represents whether or not there's a new message to be shown.
     */
    val eventMessageToShow: LiveData<Boolean>
        get() = _eventMessageToShow

    /**
     * Private and mutable field that stores the most recently received message.
     */
    private val _receivedMessage = MutableLiveData<ChatMessage>()
    /**
     * Exposes the most recently received message in an immutable fashion.
     */
    val receivedMessage: LiveData<ChatMessage>
        get() = _receivedMessage

    /**
     * The private current match time, which is updated from within this class.
     */
    private val _matchTime = MutableLiveData<MatchTime>()
    /**
     * Exposes the current match time immutably to the Fragment, to be shown in the UI.
     */
    val matchTime: LiveData<MatchTime>
        get() = _matchTime

    private var _user = User("user", R.color.blue)

    /**
     * The message ID to reply to. -1 indicates that the message is not a reply.
     */
    private var _replyToID: Int = -1

    init {
        _matchTime.value = matchTime
        _user = user
        handler.postDelayed(timerRunnable, 1000)
        _eventMessageToShow.value = false
        connectToChatAPI()
    }

    /**
     * Connects to the chat socket namespace. Registers callback functions.
     */
    private fun connectToChatAPI() {

        //Once we've established a new connection, emit events to join a specific room within the namespace.
        chatSocket.on(Socket.EVENT_CONNECT) {
            chatSocket.emit("room", room)
            chatSocket.emit("username", _user.name)
            chatSocket.emit("room", room)
        }

        //Executed on receipt of a new chat message.
        chatSocket.on("chat_message") { args ->
            //Interpret data into Kotlin Native code.

            val msgObject = args[0] as JSONObject
            val id = msgObject.get("id") as Int
            val parentID = msgObject.get("parent_id") as Int
            val username = msgObject.get("username") as String
            val userColor = msgObject.get("color") as Int
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

            //Translate into the appropriate Data objects.
            val incomingMatchTime = MatchTime(state, minutes, seconds, quarterSeconds)
            val chatMessage = ChatMessage(id, parentID, (User(username, userColor)), message, incomingMatchTime)
            Log.d("ChatViewModel", "Received. This is a reply to: $parentID")
            val timeDifference: Long = calculateDifferenceInMillis(incomingMatchTime)
            updateMessage(chatMessage, timeDifference)
        }
        chatSocket.connect()
    }

    /**
     * Calculates the difference in [MatchTime] between the received message and the current user.
     *
     * @return The difference in milliseconds between the received message and the current user's match time. Unless the current user is ahead in the match, then it returns 0.
     */
    private fun calculateDifferenceInMillis(incomingMatchTime: MatchTime): Long {
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

    /**
     * Creates a new [Runnable] object which updates the LiveData fields with the message to be shown.
     * @param chatMessage The message to be shown.
     * @param delay The number of milliseconds to wait before that message should be shown.
     */
    private fun updateMessage(chatMessage: ChatMessage, delay: Long) {
        class MessageDelayRunnable : Runnable {
            override fun run() {
                _receivedMessage.postValue(chatMessage)
                _eventMessageToShow.postValue(true)
            }
        }
        handler.postDelayed(MessageDelayRunnable(), delay)
    }

    /**
     * Sends a message to the current room.
     */
    fun sendMessage(message: String) {
        //Package the message in a JSON object, so it can emit through the socket API.
        val msgObject = JSONObject()
        msgObject.put("id", null)
        msgObject.put("parent_id", _replyToID)
        msgObject.put("username", _user.name)
        msgObject.put("color", _user.color)
        msgObject.put("message", message)

        //Package the current match time in a JSON object, so it can emit through the socket API.
        val timeObject = JSONObject()
        timeObject.put("state", _matchTime.value!!.state)
        timeObject.put("minutes", _matchTime.value!!.minutes)
        timeObject.put("seconds", _matchTime.value!!.seconds)
        timeObject.put("quarter_seconds", _matchTime.value!!.quarterSeconds)
        msgObject.put("user_time", timeObject)

        //Emit a chat_message event with the object that includes the time, user, and text.
        chatSocket.emit("chat_message", msgObject)

        _replyToID = -1 //Reset to the default of -1 (i.e. the message is not a reply).
    }

    fun disconnectFromChatroom() {
        chatSocket.disconnect()
    }

    fun reconnectToChatroom() {
        if (!chatSocket.connected()) {
            chatSocket.connect()
        }
    }

    /**
     * Reset the private _eventMessageToShow to false, so that we can observe future new messages.
     */
    fun onDisplayMessageComplete() {
        _eventMessageToShow.value = false
    }

    fun setReplyID(parentID: Int) {
        _replyToID = parentID
    }

    fun resumeTimer(matchTime: MatchTime) {
        _matchTime.value = matchTime
        handler.postDelayed(timerRunnable, 1000)
    }

    fun pauseTimer() {
        handler.removeCallbacks(timerRunnable)
    }

    fun updateMatchTime(matchTime: MatchTime) {
        _matchTime.value = matchTime
    }
}