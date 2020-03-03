package com.jlbennett.syncsports.chat


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.jlbennett.syncsports.R
import com.jlbennett.syncsports.databinding.FragmentChatBinding
import com.jlbennett.syncsports.util.MatchTime
import com.jlbennett.syncsports.util.State
import com.jlbennett.syncsports.util.User
import java.util.*


class ChatFragment : Fragment(), TimeAdjustDialogFragment.DialogListener {

    private lateinit var viewModel: ChatViewModel
    private lateinit var sharedPref: SharedPreferences
    private lateinit var viewModelFactory: ChatViewModelFactory
    private lateinit var recyclerViewAdapter: ChatMessageAdapter
    private lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentChatBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_chat, container, false
        )

        val args: ChatFragmentArgs by navArgs()
        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!
        user = readUser()
        viewModelFactory = ChatViewModelFactory(args.matchTime, args.roomName, user)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ChatViewModel::class.java)
        val timeAdjustDialogFragment = TimeAdjustDialogFragment()
        timeAdjustDialogFragment.setTargetFragment(this, 1)

        viewModel.eventMessageToShow.observe(viewLifecycleOwner, Observer { hasMessageToShow ->
            if (hasMessageToShow) {
                val chatMessage: ChatMessage =
                    viewModel.receivedMessage.value ?: ChatMessage(
                        User("X", "#000000"),
                        "Error receiving message",
                        MatchTime(State.PRE_MATCH, 0, 0, 0)
                    )
                displayMessage(chatMessage)
                viewModel.onDisplayMessageComplete()
            }
        })

        viewModel.matchTime.observe(viewLifecycleOwner, Observer { updatingMatchTime ->
            val minString = String.format("%02d", updatingMatchTime.minutes)
            val secString = String.format("%02d", updatingMatchTime.seconds)
            val stateString = when (updatingMatchTime.state) {
                State.PRE_MATCH -> "Pre-Match"
                State.FIRST_HALF -> "1st Half"
                State.HALF_TIME -> "Half Time"
                State.SECOND_HALF -> "2nd Half"
                State.FULL_TIME -> "Full Time"
            }
            val matchTimeString = "$stateString\n$minString:$secString"
            binding.timeButton.text = matchTimeString

            if(timeAdjustDialogFragment.dialogIsShown && !timeAdjustDialogFragment.timeIsUpdated) {
                timeAdjustDialogFragment.updateTime(updatingMatchTime)
            }
        })

        binding.chatroomNameText.text = args.roomName
        binding.timeButton.setOnClickListener {
            timeAdjustDialogFragment.show(parentFragmentManager, "timeAdjustDialog")
        }

        binding.chatMessageList.layoutManager = LinearLayoutManager(this.context)//Set RecyclerView LayoutManager

        recyclerViewAdapter = ChatMessageAdapter(viewModel.messages)
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

    private fun readUser(): User {
        val persistentUsername = sharedPref.getString(getString(R.string.username_key), "Username")!!
        val persistentColor = sharedPref.getString(getString(R.string.color_key), "#0B4AB0")!!
        return User(persistentUsername, persistentColor)
    }

    override fun onTimeSet(matchTime: MatchTime) {
        viewModel.updateMatchTime(matchTime)
    }

    override fun onResume() {
        super.onResume()
        viewModel.reconnectToChatroom()
        val timeJSON = sharedPref.getString("matchTime", null)
        val timeStored = sharedPref.getLong("timeStored", 0)
        if (timeJSON != null) {
            val oldMatchTime: MatchTime = Gson().fromJson(timeJSON, MatchTime::class.java)
            val currentTime = Calendar.getInstance().timeInMillis
            val elapsed = currentTime - timeStored
            val elapsedSeconds = (elapsed / 1000).toInt()
            val elapsedMinutes = elapsedSeconds / 60
            val matchTime = MatchTime(
                oldMatchTime.state,//TODO handle state
                oldMatchTime.minutes + elapsedMinutes,
                oldMatchTime.seconds + (elapsedSeconds % 60),
                0
            )
            viewModel.resumeTimer(matchTime)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseTimer()
        val timeStored = Calendar.getInstance().timeInMillis
        val timeJSON = Gson().toJson(viewModel.matchTime.value!!)
        sharedPref.edit().putString("matchTime", timeJSON).apply()
        sharedPref.edit().putLong("timeStored", timeStored).apply()

        viewModel.disconnectFromChatroom()
    }
}
