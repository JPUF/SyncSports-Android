package com.jlbennett.syncsports.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jlbennett.syncsports.util.Chatroom
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val roomSocket = IO.socket("http://syncsport.herokuapp.com/rooms")
    private val context = getApplication<Application>().applicationContext

    init {
        Log.d("HomeView", "HomeViewModel Created")
        connectToRoomAPI()
    }

    private fun connectToRoomAPI() {

        roomSocket.on(Socket.EVENT_CONNECT) {
            roomSocket.emit("room_request")
        }

        roomSocket.on("room_response") {args ->
            val roomsObject = args[0] as JSONObject
            Log.d("HomeView", "in room_response: #${roomsObject.length()}" )

            val localRoomList = mutableListOf<Chatroom>()
            val it = roomsObject.keys()
            do {
                val roomName = it.next()
                val roomDetails = roomsObject.get(roomName) as JSONObject
                val roomMemberCount = roomDetails.get("member_count") as Int
                val roomPublic = when (roomDetails.get("public")) {//Maybe cast 'as Boolean'
                    "true" -> true
                    else -> false
                }
                Log.d("HomeView", "$roomName : $roomDetails\ncount=$roomMemberCount, public=$roomPublic")
                localRoomList.add(Chatroom(roomName, roomMemberCount, roomPublic))
            } while (it.hasNext())
            _roomList.postValue(localRoomList)
            _eventRoomListPopulated.postValue(true)
        }

        roomSocket.connect()
    }

    private val _eventRoomListPopulated = MutableLiveData<Boolean>()
    val eventRoomListPopulated: LiveData<Boolean>
    get() = _eventRoomListPopulated

    private val _roomList = MutableLiveData<List<Chatroom>>()
    val roomList: LiveData<List<Chatroom>>
        get() = _roomList

    fun readAllRooms() {
        val localRoomList = mutableListOf<Chatroom>()
        val queue = Volley.newRequestQueue(context)
        val urlString = "http://syncsport.herokuapp.com/rooms"
        val roomRequest = JsonObjectRequest(Request.Method.GET, urlString, null,
            Response.Listener { response ->
                val it = response.keys()
                do {
                    val roomName = it.next()
                    val roomDetails = response.get(roomName) as JSONObject
                    val roomMemberCount = roomDetails.get("member_count") as Int
                    val roomPublic = when (roomDetails.get("public")) {//Maybe cast 'as Boolean'
                        "true" -> true
                        else -> false
                    }
                    Log.d("HomeView", "$roomName : $roomDetails\ncount=$roomMemberCount, public=$roomPublic")
                    localRoomList.add(Chatroom(roomName, roomMemberCount, roomPublic))
                } while (it.hasNext())
                _roomList.postValue(localRoomList)
                _eventRoomListPopulated.postValue(true)
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
            }
        )
        queue.add(roomRequest)
    }

    fun getRoomCount(): Int {
        return _roomList.value?.size ?: 0
    }

    fun onDisplayRoomsComplete() {
        _eventRoomListPopulated.value = false
    }

    fun disconnectFromRooms() {
        roomSocket.disconnect()
    }

    fun reconnectToRooms() {
        if(!roomSocket.connected()) {
            roomSocket.connect()
        }
    }
}