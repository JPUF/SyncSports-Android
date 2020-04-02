package com.jlbennett.syncsports.chat

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
import com.jlbennett.syncsports.R

/**
 * The adapter class to convert the raw ChatMessages into Views to be displayed in the RecyclerView.
 * @param messages All the received messages.
 */
class ChatMessageAdapter(messages: List<ChatMessage>) : RecyclerView.Adapter<ChatItemViewHolder>() {
    var data = messages
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    /**
     * Append a new message to the list of messages.
     */
    fun addMessage(message: ChatMessage) {
        data = data + message
    }

    override fun getItemCount() = data.size

    /**
     * Populate each view with the appropriate information.
     *
     * Sets the correct text colour for the user, and populates the TextView with the message text.
     */
    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        val item = data[position]
        val messageText = holder.messageTextView

        //Convert Android colour ID to RGB hex values. So it can be used with the generic Java Color class.
        val colorString = when (item.user.color) {
            R.color.blue -> "#0B4AB0"
            R.color.red -> "#910606"
            R.color.orange -> "#DE8100"
            R.color.yellow -> "#DBCA12"
            R.color.green -> "#13AD02"
            R.color.purple -> "#7D02AD"
            else -> "#0B4AB0"
        }

        //A SpannableString allows for different subsections of the string to have different formatting.
        //This is desired to have the username bold and colourful, and the body of the message looking normal.
        val messageSpannableString = SpannableString("${item.user.name}: ${item.message}")
        messageSpannableString.setSpan(
            ForegroundColorSpan(Color.parseColor(colorString)),
            0,
            item.user.name.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
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