package com.jlbennett.syncsports

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ChatMessageAdapter(messages: List<String>) : RecyclerView.Adapter<ChatItemViewHolder>() {
    //"MSG1","MSG2","MSG3","MSG4","MSG5","MSG6","MSG7","MSG8"
    var data = messages
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    fun addMessage(message: String) {
        data = data + message
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        val item = data[position]
        //holder.textView.text = item
        val usernameText = holder.messageLayout[0] as TextView
        val messageText = holder.messageLayout[1] as TextView
        usernameText.text = "UserName"
        messageText.text = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.chat_item_view, parent, false) as LinearLayout
        return ChatItemViewHolder(view)
    }
}

class ChatItemViewHolder(val messageLayout: LinearLayout): RecyclerView.ViewHolder(messageLayout)