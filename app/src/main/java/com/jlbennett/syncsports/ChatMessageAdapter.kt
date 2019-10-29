package com.jlbennett.syncsports

import android.graphics.Color
import android.graphics.Typeface.BOLD
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

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
        val messageText = holder.messageTextView

        val dummyUsers = listOf("Kenny", "AstroHound", "Kai", "EightSevenFortyFifteen")
        val username = dummyUsers[Random.nextInt(0, dummyUsers.size)]
        val userColor = when(Random.nextInt(0, 5)){
            0 -> Color.BLUE
            1 -> Color.RED
            2 -> Color.MAGENTA
            3 -> Color.DKGRAY
            4 -> Color.GREEN
            else -> Color.BLACK
        }

        val messageSpannableString = SpannableString("$username: $item")
        messageSpannableString.setSpan(ForegroundColorSpan(userColor),0, username.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        messageSpannableString.setSpan(StyleSpan(BOLD),0, username.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        messageText.text = messageSpannableString



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.chat_item_view, parent, false) as TextView
        return ChatItemViewHolder(view)
    }
}

class ChatItemViewHolder(val messageTextView: TextView): RecyclerView.ViewHolder(messageTextView)