package com.jlbennett.syncsports.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.jlbennett.syncsports.R
import com.jlbennett.syncsports.util.Chatroom

/**
 * The adapter class for the RecyclerView displaying all rooms.
 *
 * It converts the details of all chatrooms to the layout specified in room_item.xml
 *
 * @param rooms The details of all current chatrooms.
 */
class RoomAdapter(rooms: List<Chatroom>) : RecyclerView.Adapter<RoomViewHolder>() {

    private val roomList = rooms

    /**
     * The ViewHolder is a wrapper around each room_item view. This facilitates the 'recycling'.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.room_item, parent, false)
        return RoomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    /**
     * Sets the layout View objects to the appropriate information.
     *
     * e.g. Sets the text of the room's name.
     */
    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val name = roomList[position].name
        val memberCount = roomList[position].memberCount
        holder.nameText.text = name
        holder.memberText.text = "$memberCount"

        holder.itemView.setOnClickListener { view ->
            //Navigate to the SyncFragment, using the 'action' defined in the NavGraph - main_navigation.xml.
            //This includes a SafeArgs parameter 'name'.
            // SafeArgs allow data to be passed between Fragments in a type-safe manner.
            val action = HomeFragmentDirections.actionHomeFragmentToSyncFragment(name)
            Navigation.findNavController(view).navigate(action)
        }
    }
}

class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var nameText: TextView = itemView.findViewById(R.id.name_text)
    var memberText: TextView = itemView.findViewById(R.id.member_text)
}