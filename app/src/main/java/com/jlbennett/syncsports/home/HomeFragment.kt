package com.jlbennett.syncsports.home


import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.jlbennett.syncsports.databinding.FragmentHomeBinding
import com.jlbennett.syncsports.util.User
import java.util.regex.Pattern
import android.widget.TextView
import android.widget.LinearLayout
import com.jlbennett.syncsports.R


@Suppress("DEPRECATION")
class HomeFragment : Fragment(), ColorPickerDialogFragment.DialogListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var sharedPref: SharedPreferences
    private lateinit var username: String
    private lateinit var color: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.readAllRooms()//TODO need to remove onPause? read onResume?

        binding.room1Card.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSyncFragment("room1")
            storeUser(User(username, color))
            findNavController().navigate(action)
        }

        binding.room2Card.setOnClickListener {
            storeUser(User(username, color))
            val action = HomeFragmentDirections.actionHomeFragmentToSyncFragment("room2")
            findNavController().navigate(action)
        }

        binding.createRoomButton.setOnClickListener {
            storeUser(User(username, color))
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCreateRoomFragment())
        }

        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!
        val persistentUsername = sharedPref.getString(getString(R.string.username_key), null)
        val persistentColor = sharedPref.getString(getString(R.string.color_key), null) ?: "#0B4AB0"
        if (persistentUsername != null) {
            username = persistentUsername
            binding.usernameEntry.text = SpannableStringBuilder(username)
            checkUsername(username)
        }
        color = persistentColor
        binding.colorButton.background.setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY)

        binding.usernameEntry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkUsername(p0.toString().trim())
            }

            override fun afterTextChanged(p0: Editable?) {}
        })


        binding.colorButton.setOnClickListener {
            val colorPickerDialogFragment = ColorPickerDialogFragment()
            colorPickerDialogFragment.setTargetFragment(this, 1)
            colorPickerDialogFragment.show(fragmentManager!!, "colorPickerDialog")
        }

        viewModel.chatroomName.observe(this, Observer { chatroomName ->
            addChatroomCard(chatroomName)
        })

        return binding.root
    }

    private fun addChatroomCard(chatroomName: String?) {
        //TODO rooms should be displayed with cards
        //TODO preferably stored in a RecyclerView
        val chatroomText = TextView(activity)
        chatroomText.text = chatroomName
        chatroomText.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.FILL_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        binding.roomLayout.addView(chatroomText)
    }

    override fun onColorSelected(colorString: String) {
        binding.colorButton.background.setColorFilter(Color.parseColor(colorString), PorterDuff.Mode.MULTIPLY)
        color = colorString
    }

    private fun checkUsername(name: String) {
        binding.roomHeaderText.visibility = View.VISIBLE
        if (isValidUsername(name)) {
            binding.usernameValidText.text = resources.getString(R.string.valid)
            binding.usernameValidText.setTextColor(ContextCompat.getColor(context!!, R.color.colorValid))
            binding.roomHeaderText.text = resources.getString(R.string.popular_rooms)
            binding.roomScroll.visibility = View.VISIBLE
            binding.createRoomButton.visibility = View.VISIBLE
            username = name
        } else {
            binding.usernameValidText.text = resources.getString(R.string.invalid)
            binding.usernameValidText.setTextColor(ContextCompat.getColor(context!!, R.color.colorInvalid))
            binding.roomHeaderText.text = resources.getString(R.string.no_user)
            binding.roomScroll.visibility = View.INVISIBLE
            binding.createRoomButton.visibility = View.INVISIBLE
        }
    }

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

    private fun storeUser(user: User) {
        val preferenceEditor = sharedPref.edit()
        preferenceEditor?.putString(getString(R.string.username_key), user.name)
        preferenceEditor?.putString(getString(R.string.color_key), user.color)
        preferenceEditor?.apply()
    }
}
