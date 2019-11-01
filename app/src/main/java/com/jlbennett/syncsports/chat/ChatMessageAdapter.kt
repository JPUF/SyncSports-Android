package com.jlbennett.syncsports.chat

import android.graphics.Typeface.BOLD
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jlbennett.syncsports.R

class ChatMessageAdapter(messages: List<ChatMessage>) : RecyclerView.Adapter<ChatItemViewHolder>() {
    //"MSG1","MSG2","MSG3","MSG4","MSG5","MSG6","MSG7","MSG8"
    var data = messages
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    fun addMessage(message: ChatMessage) {
        data = data + message
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        val item = data[position]
        val messageText = holder.messageTextView

        val messageSpannableString = SpannableString("${item.user.name}: ${item.message}")
        messageSpannableString.setSpan(ForegroundColorSpan(item.user.color),0, item.user.name.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        messageSpannableString.setSpan(StyleSpan(BOLD),0, item.user.name.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        messageText.text = messageSpannableString
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.chat_item_view, parent, false) as TextView
        return ChatItemViewHolder(view)
    }
}

class ChatItemViewHolder(val messageTextView: TextView): RecyclerView.ViewHolder(messageTextView)