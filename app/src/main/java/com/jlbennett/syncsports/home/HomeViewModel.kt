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

/**
 * ViewModel for the HomeFragment.
 *
 * This fragment is exposed via the ViewModelProvider class. It's a singleton, and survives Android configuration changes.
 *
 * @param application The android application context.
 */
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Reference to the socket server 'rooms' namespace. Note: http not https.
     */
    private val roomSocket = IO.socket("http://syncsport.herokuapp.com/rooms")

    init {
        Log.d("HomeView", "HomeViewModel Created")
        connectToRoomAPI()
    }

    /**
     * Assigns callback functions to be executed on socket events.
     */
    private fun connectToRoomAPI() {

        //Once connected to the room namespace, emit a request to receive rooms.
        roomSocket.on(Socket.EVENT_CONNECT) {
            roomSocket.emit("room_request")
        }

        //Listen for responses to the room request. Responses include the list of current chatrooms.
        roomSocket.on("room_response") {body ->
            val localRoomList = mutableListOf<Chatroom>() //Local list to be populated with received rooms.
            val roomsObject = body[0] as JSONObject? //The rooms are stored in the first element of the response body.
            if(roomsObject != null){ //If a non-zero number of rooms have been received.
                val it = roomsObject.keys()
                do { //Iterate through all received rooms.
                    val roomName = it.next()
                    val roomDetails = roomsObject.get(roomName) as JSONObject

                    //Convert each entry to a Kotlin native type.
                    val roomMemberCount = roomDetails.get("member_count") as Int
                    val roomPublic = when (roomDetails.get("public")) {
                        "true" -> true
                        else -> false
                    }

                    //Add to the local list, as a Chatroom object.
                    localRoomList.add(Chatroom(roomName, roomMemberCount, roomPublic))
                } while (it.hasNext())
            }

            //At this point, localRoomList is populated with all existing rooms (may be zero).
            //The viewModel's private _roomList is updated, so it now includes all rooms.
            _roomList.postValue(localRoomList)

            //The private boolean is set to true.
            // This change is then observed by the Fragment, which in turn knows it's time to read the public roomList
            _eventRoomListPopulated.postValue(true)
        }

        //Use the Socket API to connect to the room namespace, which results in a EVENT_CONNECT event being emitted.
        roomSocket.connect()
    }

    /**
     * The private and mutable boolean that represents whether the roomList has been populated with rooms.
     */
    private val _eventRoomListPopulated = MutableLiveData<Boolean>()
    /**
     * The public and immutable boolean. The Fragment observes changes in this variable to know when to read the roomList.
     */
    val eventRoomListPopulated: LiveData<Boolean>
        get() = _eventRoomListPopulated

    /**
     * The private and mutable list of Chatrooms.
     */
    private val _roomList = MutableLiveData<List<Chatroom>>()
    /**
     * The public and immutable list of Chatrooms. This exposes the private list of Chatrooms to the Fragment.
     */
    val roomList: LiveData<List<Chatroom>>
        get() = _roomList

    /**
     * The current number of rooms.
     * @return 0 if the room list is null. (Room list is only null when there's no rooms)
     */
    fun getRoomCount(): Int {
        return _roomList.value?.size ?: 0
    }

    /**
     * Reset the boolean, so future changes can be observed.
     */
    fun onDisplayRoomsComplete() {
        _eventRoomListPopulated.value = false
    }

    /**
     * Disconnect from the room namespace.
     */
    fun disconnectFromRooms() {
        roomSocket.disconnect()
    }

    /**
     * Reconnect to the room namespace if currently disconnected.
     */
    fun reconnectToRooms() {
        if(!roomSocket.connected()) {
            roomSocket.connect()
        }
    }
}