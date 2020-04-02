package com.jlbennett.syncsports.home


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jlbennett.syncsports.R
import com.jlbennett.syncsports.databinding.FragmentHomeBinding
import com.jlbennett.syncsports.util.User
import java.util.regex.Pattern

/**
 * The View class for the Home screen.
 *
 * This screen is responsible for username entry, colour selection, and displaying the active set of chatrooms.
 */
class HomeFragment : Fragment(), ColorPickerDialogFragment.DialogListener {

    /**
     * The databinding object. It contains references to all View objects in this Fragment's layout XML.
     *
     * References to each view object are generated as members of this object at compile time.
     */
    private lateinit var binding: FragmentHomeBinding

    /**
     * Reference to basic local storage, for username and preferred colour.
     */
    private lateinit var sharedPref: SharedPreferences

    private lateinit var viewModel: HomeViewModel
    private lateinit var roomAdapter: RoomAdapter
    private lateinit var username: String
    private var color: Int = R.color.blue

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        //Inflate the layout for this fragment, and populate binding object with view references.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        //Get singleton instance of ViewModel.
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.roomRecycler.layoutManager = LinearLayoutManager(activity)

        //Set inline callback function to listen for when the list of rooms has been populated.
        viewModel.eventRoomListPopulated.observe(viewLifecycleOwner, Observer { hasPopulatedRooms ->
            if (hasPopulatedRooms) {
                roomAdapter = RoomAdapter(viewModel.roomList.value!!)
                roomAdapter.notifyDataSetChanged()
                binding.roomRecycler.adapter = roomAdapter //Give the recycler an adapter populated with all rooms.
                viewModel.onDisplayRoomsComplete()
                setRoomHeader(R.string.popular_rooms)
            }
        })

        //Navigate to CreateRoomFragment when button is clicked.
        binding.createRoomButton.setOnClickListener {
            //Since the user is navigating away, store their details.
            storeUser(User(username, color))

            //Use the Navigation component to go to the appropriate Fragment.
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCreateRoomFragment())
        }

        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!

        //Read colour from local storage. If it's not been set yet, then default to blue.
        val persistentColor = sharedPref.getInt(getString(R.string.color_key), R.color.blue)
        color = persistentColor
        //Set the colour of the button to the given colour.
        binding.colorButton.backgroundTintList = ContextCompat.getColorStateList(context!!, color)

        //Retrieve name from local storage.
        val persistentUsername = sharedPref.getString(getString(R.string.username_key), null)
        if (persistentUsername != null) { //If name has been previously entered.
            username = persistentUsername
            binding.usernameEntry.text = SpannableStringBuilder(username)//Load name into entry box.
            checkUsername(username)
        }

        //Listen for when the user is typing their username.
        binding.usernameEntry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Each time the text changes, the username is checked for validity. Excess whitespace is removed.
                checkUsername(p0.toString().trim())
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        //Show the pop-up dialog fragment when the user clicks on the colour button.
        binding.colorButton.setOnClickListener {
            val colorPickerDialogFragment = ColorPickerDialogFragment()

            //Specify this class as the implementation of the callback function.
            colorPickerDialogFragment.setTargetFragment(this, 1)
            colorPickerDialogFragment.show(parentFragmentManager, "colorPickerDialog")
        }
        return binding.root
    }

    /**
     * The callback function for the colour selection `dialog'.
     *
     * This function is called when the user selects a colour.
     * @param colorID The Android colour ID of the chosen colour.
     */
    override fun onColorSelected(colorID: Int) {
        //Set the colour of the button to the given colour.
        binding.colorButton.backgroundTintList = ContextCompat.getColorStateList(context!!, colorID)
        color = colorID
        storeColor(color)
    }

    override fun onPause() {
        super.onPause()
        viewModel.disconnectFromRooms()
    }

    override fun onResume() {
        super.onResume()
        viewModel.reconnectToRooms()
    }

    /**
     * Updates the TextView above the list of rooms.
     *
     * This TextView is responsible for communicating numerous things, and is therefore handled by this function.
     * @param stringID The Android resource ID for the string to be shown.
     */
    private fun setRoomHeader(stringID: Int){
        if(viewModel.getRoomCount() == 0){ // If there are no rooms, the string given as parameter is ignored.
            binding.roomHeaderText.text = getString(R.string.no_rooms)
        } else {
            binding.roomHeaderText.text = getString(stringID)
        }
    }

    /**
     * Calls [isValidUsername] to determine validity. Updates the UI to give feedback to user.
     *
     * @param name The username to be checked
     */
    private fun checkUsername(name: String) {
        binding.roomHeaderText.visibility = View.VISIBLE
        if (isValidUsername(name)) {
            //The user is told their name is valid.
            binding.usernameValidText.text = resources.getString(R.string.valid)
            //The next colour is set to a nice positive green.
            binding.usernameValidText.setTextColor(ContextCompat.getColor(context!!, R.color.colorValid))

            //Since it's valid, the user is allowed to view (therefore navigate) to the rooms.
            //The header is updated appropriately.
            setRoomHeader(R.string.popular_rooms)
            //The rooms are shown.
            binding.roomRecycler.visibility = View.VISIBLE
            //The button to create new rooms is shown (only accessible with a valid name).
            binding.createRoomButton.visibility = View.VISIBLE

            //The valid name is stored in local storage (sharedPrefs).
            username = name
            storeUser(User(username, color))

        } else { //Invalid username
            //User is given feedback.
            binding.usernameValidText.text = resources.getString(R.string.invalid)
            //Feedback text is given a negative looking red :[
            binding.usernameValidText.setTextColor(ContextCompat.getColor(context!!, R.color.colorInvalid))
            //The room header text is updated appropriately.
            setRoomHeader(R.string.no_user)

            //The room views are made invisible. So the user cannot access them.
            binding.roomRecycler.visibility = View.INVISIBLE
            binding.createRoomButton.visibility = View.INVISIBLE
        }
    }

    /**
     * Tests if the username is appropriate for use.
     *
     * Valid names are those that do not contain special characters (so only digits and letters), are between 3 and 16 characters in length, and do not contain spaces.
     *
     * @param username name to be tested
     * @return True if valid, else false.
     */
    private fun isValidUsername(username: String): Boolean {
        val regexPattern = Pattern.compile("[^a-zA-Z0-9]")
        if (username.length <= 3 || username.length >= 16)
            return false
        else if (username.contains(' '))
            return false
        else if (regexPattern.matcher(username).find())
            return false

        return true//returns true (valid) if previous checks are passed
    }

    /**
     * Stores a given Android colour ID integer into the phones local storage via SharedPreferences. Under tag "color"
     *
     * @param colorID Android colour ID.
     */
    private fun storeColor(colorID: Int) {
        val preferenceEditor = sharedPref.edit()
        preferenceEditor?.putInt(getString(R.string.color_key), colorID)
        preferenceEditor?.apply()
    }

    /**
     * Stores a given name into sharedPrefences. Under tag "username"
     *
     * @param name Name to store.
     */
    private fun storeName(name: String) {
        val preferenceEditor = sharedPref.edit()
        preferenceEditor?.putString(getString(R.string.username_key), name)
        preferenceEditor?.apply()
    }

    /**
     * A convenience function that calls [storeColor] and [storeUser]
     */
    private fun storeUser(user: User) {
        Log.d("username store", "Storing: ${user.name}")
        storeColor(user.color)
        storeName(user.name)
    }
}
