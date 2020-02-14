package com.jlbennett.syncsports.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
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
        return roomList.size
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val name = roomList[position].name
        val memberCount = roomList[position].memberCount
        holder.nameText.text = name
        holder.memberText.text = "$memberCount"

        holder.itemView.setOnClickListener { view ->
            val action = HomeFragmentDirections.actionHomeFragmentToSyncFragment(name)
            Navigation.findNavController(view).navigate(action)
        }
    }
}

class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var nameText: TextView = itemView.findViewById(R.id.name_text)
    var memberText: TextView = itemView.findViewById(R.id.member_text)
}