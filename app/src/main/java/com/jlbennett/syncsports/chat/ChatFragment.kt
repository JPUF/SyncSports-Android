package com.jlbennett.syncsports.chat


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
    private lateinit var recyclerViewAdapter: ChatMessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentChatBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_chat, container, false
        )

        viewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)

        viewModel.eventMessageToShow.observe(this, Observer {hasMessageToShow ->
            if (hasMessageToShow) {
                displayMessage(ChatMessage(User("mes", Color.RED), "Test Message"))
                viewModel.onDisplayMessageComplete()
                //TODO test to see if test message is displayed.
            }
        })

        viewModel.connectToChatAPI()

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

            viewModel.sendMessage(chatMessage.toString())
        }
        return binding.root
    }

    fun displayMessage(chatMessage: ChatMessage) {
        activity!!.runOnUiThread {
            //Ugly
            recyclerViewAdapter.addMessage(chatMessage)
        }
    }

}
