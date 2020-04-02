package com.jlbennett.syncsports.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jlbennett.syncsports.util.Chatroom
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val roomSocket = IO.socket("http://syncsport.herokuapp.com/rooms")

    init {
        Log.d("HomeView", "HomeViewModel Created")
        connectToRoomAPI()
    }

    private fun connectToRoomAPI() {

        roomSocket.on(Socket.EVENT_CONNECT) {
            roomSocket.emit("room_request")
        }

        roomSocket.on("room_response") {args ->
            val localRoomList = mutableListOf<Chatroom>()
            val roomsObject = args[0] as JSONObject?
            if(roomsObject == null){
                Log.d("HomeView", "in room_response: #0" )
            } else {
                Log.d("HomeView", "in room_response: #${roomsObject.length()}" )

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
            }
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