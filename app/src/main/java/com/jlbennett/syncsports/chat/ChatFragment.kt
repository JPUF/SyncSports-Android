package com.jlbennett.syncsports.chat


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jlbennett.syncsports.R
import com.jlbennett.syncsports.databinding.FragmentChatBinding
import com.jlbennett.syncsports.util.User


class ChatFragment : Fragment() {


    private lateinit var viewModel: ChatViewModel
    private lateinit var viewModelFactory: ChatViewModelFactory
    private lateinit var recyclerViewAdapter: ChatMessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentChatBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_chat, container, false
        )

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!
        val persistentUsername = sharedPref.getString(getString(R.string.username_key), "Username")!!

        val args: ChatFragmentArgs by navArgs()
        viewModelFactory = ChatViewModelFactory(args.matchTime, args.roomName, persistentUsername)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)

        viewModel.eventMessageToShow.observe(this, Observer { hasMessageToShow ->
            if (hasMessageToShow) {
                val chatMessage: ChatMessage =
                    viewModel.receivedMessage.value ?: ChatMessage(User("X", Color.BLACK), "Error receiving message")

                displayMessage(chatMessage)
                viewModel.onDisplayMessageComplete()
            }
        })

        viewModel.matchTime.observe(this, Observer { updatingMatchTime ->
            val minString = String.format("%02d", updatingMatchTime.minutes)
            val secString = String.format("%02d", updatingMatchTime.seconds)
            val matchTimeString = "MATCH ${updatingMatchTime.state} -- $minString:$secString"
            binding.timeText.text = matchTimeString
        })

        binding.chatMessageList.layoutManager = LinearLayoutManager(this.context)//Set RecyclerView LayoutManager

        recyclerViewAdapter = ChatMessageAdapter(viewModel.dummyMessages)
        binding.chatMessageList.adapter = recyclerViewAdapter

        binding.sendButton.setOnClickListener {
            val chatMessage = binding.inputText.text
            binding.chatMessageList.scrollToPosition(recyclerViewAdapter.itemCount - 1)
            binding.inputText.setText(R.string.empty)

            viewModel.sendMessage(chatMessage.toString())
        }
        return binding.root
    }

    private fun displayMessage(chatMessage: ChatMessage) {
        activity!!.runOnUiThread {
            //Ugly
            recyclerViewAdapter.addMessage(chatMessage)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.reconnectToChatroom()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopUpdatingTimer()
        viewModel.disconnectFromChatroom()
    }
}
