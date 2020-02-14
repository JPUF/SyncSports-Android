package com.jlbennett.syncsports.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jlbennett.syncsports.util.Chatroom
import org.json.JSONObject

class HomeViewModel(application: Application) : AndroidViewModel(application)  {
    private val context = getApplication<Application>().applicationContext
    init {
        Log.d("HomeView", "HomeViewModel Created")
    }

    //TODO can't work like this. It's async.
    fun readAllRooms() : List<Chatroom> {
        val roomList = mutableListOf<Chatroom>()
        val queue = Volley.newRequestQueue(context)
        val urlString = "http://syncsport.herokuapp.com/rooms"
        val roomRequest = JsonObjectRequest(Request.Method.GET, urlString,null,
            Response.Listener { response ->
                val it = response.keys()
                do {
                    val roomName = it.next()
                    val roomDetails = response.get(roomName) as JSONObject
                    val roomMemberCount = roomDetails.get("member_count") as Int
                    val roomPublic = when(roomDetails.get("public")){//Maybe cast 'as Boolean'
                        "true" -> true
                        else -> false
                    }
                    Log.d("HomeView", "$roomName : $roomDetails\ncount=$roomMemberCount, public=$roomPublic")
                    roomList.add(Chatroom(roomName, roomMemberCount,roomPublic))
                } while (it.hasNext())
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
            }
        )
        queue.add(roomRequest)
        Log.d("RoomRecycler", "list size in ReadAllRooms() = ${roomList.size}")
        return roomList
    }
}