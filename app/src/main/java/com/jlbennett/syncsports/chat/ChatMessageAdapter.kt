package com.jlbennett.syncsports.chat

import android.graphics.Color
import android.graphics.Typeface.BOLD
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.jlbennett.syncsports.R




/**
 * The adapter class to convert the raw ChatMessages into Views to be displayed in the RecyclerView.
 * @param messages All the received messages.
 */
class ChatMessageAdapter(messages: List<ChatMessage>, listener: ReplyCallback) :
    RecyclerView.Adapter<ChatItemViewHolder>() {
    private var data = messages
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var clickListener: ReplyCallback = listener

    /**
     * Inserts the given [ChatMessage] into the correct position in the list of messages.
     *
     * This position would typically be at the bottom of the list. However, because of asynchronous communication, the message may need to be inserted before more recent messages.
     */
    fun insertMessage(message: ChatMessage) {

        val indexToInsert = indexToInsertMessage(message)
        if (indexToInsert == -1) {
            data = data + message
        } else {
            val newList = data.toMutableList()
            newList.add(indexToInsert, message)
            data = newList
        }
        notifyDataSetChanged()
    }

    /**
     * Calculates the index within the message list that the incoming message should be inserted to.
     *
     * This method considers replies as well. As replies should be inserted beneath their parent. Replies are also sorted by MatchTime within their own conversation thread.
     *
     * @return Either the calculated index, or -1 if the message should instead simply be appended to the end of the existing list.
     */
    private fun indexToInsertMessage(message: ChatMessage): Int {
        //True when message is a reply. In which case, it should be inserted at the correct position within the replies thread.
        if(message.parentID != -1) {
            val parentIndex = indexOfParent(message.parentID)
            var lastReplyIndex = parentIndex
            val replyIterator = data.listIterator(parentIndex)
            val baseID = replyIterator.next().id
            while (replyIterator.hasNext()) {
                val reply = replyIterator.next()
                if(reply.parentID != baseID) break
                if(isBeforeExistingMessage(message, reply)) {
                    return data.lastIndexOf(reply)
                }
                lastReplyIndex++
            }
            return lastReplyIndex + 1
        }

        val messageListIterator = data.listIterator(data.size)
        while (messageListIterator.hasPrevious()) {
            val existingMessage = messageListIterator.previous()
            if (isBeforeExistingMessage(message, existingMessage)) {
                val indexToInsert = data.lastIndexOf(existingMessage)
                Log.d("ChatAdapter", "Insert at: $indexToInsert, is last? ${indexToInsert == data.lastIndex}")
                return indexToInsert
            }
        }
        return -1
    }

    private fun indexOfParent(parentID: Int): Int {
        val messageListIterator = data.listIterator(data.size)
        while (messageListIterator.hasPrevious()) {
            val itIndex = messageListIterator.previousIndex()
            val message = messageListIterator.previous()
            Log.d("ChatAdapter","Doing iteration, itIndex = $itIndex")
            if(message.id == parentID) return itIndex
        }
        return 0
    }

    /**
     * Compare two MatchTime objects.
     * @return True if the new message was sent before the existing message (in MatchTime).
     */
    private fun isBeforeExistingMessage(newMessage: ChatMessage, existingMessage: ChatMessage): Boolean {
        val newTime = newMessage.matchTime
        val existingTime = existingMessage.matchTime

        Log.d("ChatAdapter", "${newTime.readableString()} compared to ${existingTime.readableString()}}")
        Log.d(
            "ChatAdapter",
            "new: ${newMessage.message} id = ${newMessage.id}.    old: ${existingMessage.message} id = ${existingMessage.id}"
        )

        if (newTime.minutes > existingTime.minutes) return false

        if (newTime.minutes == existingTime.minutes) {
            if (newTime.seconds > existingTime.seconds) return false

            return if (newTime.seconds == existingTime.seconds) {
                newTime.quarterSeconds > existingTime.quarterSeconds
            } else true
        }
        return true
    }

    override fun getItemCount() = data.size

    /**
     * Populate each view with the appropriate information.
     *
     * Sets the correct text colour for the user, and populates the TextView with the message text.
     */
    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        val item = data[position]
        val parentConstraintLayout = holder.itemView as LinearLayout
        val timeText = parentConstraintLayout[1] as TextView
        val messageText = parentConstraintLayout[2] as TextView

        val matchTimeString = item.matchTime.readableString()
        timeText.text = matchTimeString

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
            item.user.name.length + 1,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        messageSpannableString.setSpan(
            StyleSpan(BOLD),
            0,
            item.user.name.length + 1,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        messageText.text = messageSpannableString

        if(item.parentID != -1) { //If the message is a reply.
            //Indent message by setting line to be visible, this shows conversation threads.
            val param = LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1.25f)
            holder.itemView[1].layoutParams = param
            holder.itemView[0].visibility = View.VISIBLE
        } else {
            val param = LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1f)
            holder.itemView[1].layoutParams = param
            holder.itemView[0].visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            clickListener.setupEntryForReply(item)
        }
    }

    /**
     * Inflates view for each item in the list.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.chat_item_view, parent, false) as LinearLayout
        return ChatItemViewHolder(view)
    }
}

/**
 * This class acts as a wrapper around each individual View within the RecyclerView.
 */
class ChatItemViewHolder(chatItemLayout: LinearLayout) : RecyclerView.ViewHolder(chatItemLayout)

interface ReplyCallback {
    fun setupEntryForReply(parentMessage: ChatMessage)
}