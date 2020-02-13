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

class HomeViewModel(application: Application) : AndroidViewModel(application)  {
    private val context = getApplication<Application>().applicationContext
    init {
        Log.d("HomeView", "HomeViewModel Created")
    }

    private val _chatroomName = MutableLiveData<String>()
    val chatroomName: LiveData<String>
        get() = _chatroomName

    fun readAllRooms() {
        val queue = Volley.newRequestQueue(context)
        val urlString = "http://syncsport.herokuapp.com/rooms"
        val roomRequest = JsonObjectRequest(Request.Method.GET, urlString,null,
            Response.Listener { response ->
                val it = response.keys()
                do {
                    val roomName = it.next()
                    val roomDetails = response.get(roomName)
                    Log.d("HomeView", "$roomName : $roomDetails")
                    _chatroomName.value = roomName
                } while (it.hasNext())
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
            }
        )
        queue.add(roomRequest)
    }
}