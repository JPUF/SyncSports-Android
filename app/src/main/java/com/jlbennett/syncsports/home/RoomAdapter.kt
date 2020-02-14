package com.jlbennett.syncsports.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jlbennett.syncsports.R
import com.jlbennett.syncsports.util.Chatroom

class RoomAdapter(rooms: List<Chatroom>) : RecyclerView.Adapter<RoomViewHolder>() {

    private val roomList = rooms

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.room_item, parent, false)
        return RoomViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("RoomRecycler", "list size = ${roomList.size}")
        return roomList.size
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val name = roomList[position].name
        val memberCount = roomList[position].memberCount
        holder.nameText.text = name
        holder.memberText.text = "$memberCount"
        Log.d("RoomRecycler", "recycler name = $name")
    }
}

class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var nameText: TextView = itemView.findViewById(R.id.name_text)
    var memberText: TextView = itemView.findViewById(R.id.member_text)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        Log.d("RoomRecycler", "onClick: name = ${nameText.text}")
    }

}