package com.jlbennett.syncsports.chat


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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

/**
 * The View logic for the Chat screen. Handles UI logic.
 */
class ChatFragment : Fragment(), TimeAdjustDialogFragment.DialogListener {

    /**
     * Reference to the ViewModel singleton. This handles the fragment's dynamic data, including message i/o.
     */
    private lateinit var viewModel: ChatViewModel

    /**
     * The a factory class, to allow for a ViewModel with a constructor parameters.
     */
    private lateinit var viewModelFactory: ChatViewModelFactory

    /**
     * Used for reading from local storage.
     */
    private lateinit var sharedPref: SharedPreferences
    private lateinit var recyclerViewAdapter: ChatMessageAdapter

    /**
     * The current user. Includes their name and colour.
     */
    private lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentChatBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_chat, container, false
        )

        //Reference to all arguments sent to this fragment when navigated to.
        val args: ChatFragmentArgs by navArgs()
        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!
        user = readUser()
        viewModelFactory = ChatViewModelFactory(args.matchTime, args.roomName, user)
        //Get reference to ViewModel singleton.
        viewModel = ViewModelProvider(this, viewModelFactory).get(ChatViewModel::class.java)


        val timeAdjustDialogFragment = TimeAdjustDialogFragment()
        timeAdjustDialogFragment.setTargetFragment(this, 1)

        //Listen for changes in the view model's publically accesible (immutable) eventMessageToShow.
        viewModel.eventMessageToShow.observe(viewLifecycleOwner, Observer { hasMessageToShow ->
            //We're only interested in when the LiveData value has been changed from false to true. Not visa versa.
            if (hasMessageToShow) {
                val chatMessage: ChatMessage =
                    viewModel.receivedMessage.value ?: ChatMessage(
                        User("X", R.color.blue),
                        "Error receiving message",
                        MatchTime(State.PRE_MATCH, 0, 0, 0)
                    )
                displayMessage(chatMessage)

                //Signal that the current message has been shown, we're ready for the next one.
                viewModel.onDisplayMessageComplete()
            }
        })

        //Listen for changes in the Match Time. This changes with every passing second.
        viewModel.matchTime.observe(viewLifecycleOwner, Observer { updatingMatchTime ->
            val timeString = updatingMatchTime.readableString()
            val stateString = when (updatingMatchTime.state) {
                State.PRE_MATCH -> "Pre-Match"
                State.FIRST_HALF -> "1st Half"
                State.HALF_TIME -> "Half Time"
                State.SECOND_HALF -> "2nd Half"
                State.FULL_TIME -> "Full Time"
            }
            val matchTimeString = "$stateString\n$timeString"
            //Display the updated match time.
            binding.timeButton.text = matchTimeString

            //Set the time shown in the DialogFragment to be respective to the time in this Fragment.
            if (timeAdjustDialogFragment.dialogIsShown && !timeAdjustDialogFragment.timeIsUpdated) {
                timeAdjustDialogFragment.updateTime(updatingMatchTime)
            }
        })

        binding.chatroomNameText.text = args.roomName

        binding.timeButton.setOnClickListener {
            timeAdjustDialogFragment.show(parentFragmentManager, "timeAdjustDialog")
        }

        binding.chatMessageList.layoutManager = LinearLayoutManager(this.context)//Set RecyclerView LayoutManager

        //Populate adapter with current list of messages. Mutable so new messages can be inserted.
        recyclerViewAdapter = ChatMessageAdapter(viewModel.messages.toMutableList())
        binding.chatMessageList.adapter = recyclerViewAdapter

        //Handle the event of the user pressing the send button.
        binding.sendButton.setOnClickListener {
            val chatMessage = binding.inputText.text

            if(chatMessage.isNotBlank()) //Only process non blank messages (can't send just whitespace).
            {
                //Scroll the recycler view to the bottom.
                binding.chatMessageList.scrollToPosition(recyclerViewAdapter.itemCount - 1)

                //Reset the entry, for another message to be sent.
                binding.inputText.setText(R.string.empty)

                viewModel.sendMessage(chatMessage.toString())
            }

            //Hide the soft keyboard.
            binding.inputText.let { v ->
                val manager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                manager?.hideSoftInputFromWindow(v.windowToken, 0)
            }

        }

        //Handle the user pressing the 'send' button that's integrated into the soft keyboard.
        binding.inputText.setOnEditorActionListener { view, actionID, _ ->
            return@setOnEditorActionListener when (actionID) {
                EditorInfo.IME_ACTION_SEND -> {
                    val chatMessage = view.text
                    if(chatMessage.isNotBlank())
                    {
                        binding.chatMessageList.scrollToPosition(recyclerViewAdapter.itemCount - 1)
                        binding.inputText.setText(R.string.empty)
                        viewModel.sendMessage(chatMessage.toString())
                    }
                    view?.let { v ->
                        val manager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                        manager?.hideSoftInputFromWindow(v.windowToken, 0)
                    }
                    true
                }
                else -> false
            }
        }

        return binding.root
    }

    /**
     * Adds a message to the adapter.
     */
    private fun displayMessage(chatMessage: ChatMessage) {
        activity!!.runOnUiThread {
            recyclerViewAdapter.insertMessage(chatMessage)
        }
    }

    /**
     * Reads the locally stored user object
     */
    private fun readUser(): User {
        val persistentUsername = sharedPref.getString(getString(R.string.username_key), "Username")!!
        val persistentColor = sharedPref.getInt(getString(R.string.color_key), R.color.blue)
        return User(persistentUsername, persistentColor)
    }

    /**
     * Callback function for an updated time.
     */
    override fun onTimeSet(matchTime: MatchTime) {
        viewModel.updateMatchTime(matchTime)
    }

    /**
     * Handle user returning to the chat screen after having navigated away.
     */
    override fun onResume() {
        super.onResume()
        viewModel.reconnectToChatroom()
        //Read stored match time.
        val timeJSON = sharedPref.getString("matchTime", null)
        //Read the time that match time was stored.
        val timeStored = sharedPref.getLong("timeStored", 0)
        if (timeJSON != null) {
            val oldMatchTime: MatchTime = Gson().fromJson(timeJSON, MatchTime::class.java)
            val currentTime = Calendar.getInstance().timeInMillis

            //Calculate how much time has elapsed since the user navigated away.

            val elapsed = currentTime - timeStored
            val elapsedSeconds = (elapsed / 1000).toInt()
            val elapsedMinutes = elapsedSeconds / 60
            val matchTime = MatchTime(
                oldMatchTime.state,
                oldMatchTime.minutes + elapsedMinutes,
                oldMatchTime.seconds + (elapsedSeconds % 60),
                0
            )

            //Update the timer with the correct match time
            viewModel.resumeTimer(matchTime)
        }
    }

    /**
     * Handle the user navigating away from the chat screen.
     */
    override fun onPause() {
        super.onPause()
        viewModel.pauseTimer()

        //the time (in milliseconds UTC) that the user navigated away.
        val timeStored = Calendar.getInstance().timeInMillis

        //Convert the current MatchTime to a format that can be saved in SharedPreferences (JSON).
        val timeJSON = Gson().toJson(viewModel.matchTime.value!!)
        sharedPref.edit().putString("matchTime", timeJSON).apply()
        sharedPref.edit().putLong("timeStored", timeStored).apply()

        viewModel.disconnectFromChatroom()
    }
}
