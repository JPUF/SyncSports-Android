package com.jlbennett.syncsports.chat


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jlbennett.syncsports.R
import com.jlbennett.syncsports.databinding.FragmentChatBinding
import com.jlbennett.syncsports.util.User
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.util.*


class ChatFragment : Fragment() {

    private val socket = IO.socket("http://192.168.122.1:4000")
    private lateinit var viewModel: ChatViewModel
    private lateinit var recyclerViewAdapter: ChatMessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentChatBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_chat, container, false
        )

        viewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)

        connectToChatAPI()

        val args: ChatFragmentArgs by navArgs()
        val matchTime = args.matchTime
        val matchTimeString = "MATCH ${matchTime.state} -- ${matchTime.minutes}:${matchTime.seconds}"
        binding.timeText.text = matchTimeString
        binding.chatMessageList.layoutManager = LinearLayoutManager(this.context)//Set RecyclerView LayoutManager
        
        recyclerViewAdapter = ChatMessageAdapter(viewModel.dummyMessages)
        binding.chatMessageList.adapter = recyclerViewAdapter

        binding.sendButton.setOnClickListener {
            val chatMessage = binding.inputText.text
            binding.chatMessageList.scrollToPosition(recyclerViewAdapter.itemCount - 1)
            binding.inputText.setText(R.string.empty)

            sendMessage(chatMessage.toString())
        }

        return binding.root
    }

    private fun connectToChatAPI() {
        Log.d("ChatNetworkLog", "connectToChatAPI Called")
        socket.on(Socket.EVENT_CONNECT) {
            val usernameString = "AndroidApp"
            socket.emit("username", usernameString)
        }


        socket.on("chat_message") { args ->
            val msgObject = args[0] as JSONObject
            val username: String = msgObject.get("username") as String
            val usercolor: String = msgObject.get("color") as String
            val message: String = msgObject.get("message") as String
            Log.d("usercolour", "$username - $usercolor - $message")
            displayMessage(User(username, Color.parseColor(usercolor)), message)
        }

        socket.connect()
    }

    private fun sendMessage(message: String) {
        val msgObject = JSONObject()
        msgObject.put("username", "AndroidApp")
        msgObject.put("color", "#5e0104")
        msgObject.put("message", message)
        msgObject.put("user_time", Date().time)
        socket.emit("chat_message", msgObject)
    }

    private fun displayMessage(user: User, message: String) {
        val chatMessage = ChatMessage(User(user.name, user.color), message)
        activity!!.runOnUiThread {
            //Ugly
            recyclerViewAdapter.addMessage(chatMessage)
        }
    }

}
