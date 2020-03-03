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


class HomeFragment : Fragment(), ColorPickerDialogFragment.DialogListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var sharedPref: SharedPreferences
    private lateinit var roomAdapter: RoomAdapter
    private lateinit var username: String
    private var color: Int = R.color.blue

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.roomRecycler.layoutManager = LinearLayoutManager(activity)
        viewModel.readAllRooms()

        viewModel.eventRoomListPopulated.observe(viewLifecycleOwner, Observer { hasPopulatedRooms ->
            if(hasPopulatedRooms) {
                roomAdapter = RoomAdapter(viewModel.roomList.value!!)//May be null, on failed API calls.
                roomAdapter.notifyDataSetChanged()
                binding.roomRecycler.adapter = roomAdapter
                viewModel.onDisplayRoomsComplete()
            }
        })

        binding.createRoomButton.setOnClickListener {
            storeUser(User(username, color))
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCreateRoomFragment())
        }

        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!

        val persistentColor = sharedPref.getInt(getString(R.string.color_key), R.color.blue)
        color = persistentColor
        binding.colorButton.backgroundTintList = ContextCompat.getColorStateList(context!!, color)

        val persistentUsername = sharedPref.getString(getString(R.string.username_key), null)
        if (persistentUsername != null) {
            username = persistentUsername
            binding.usernameEntry.text = SpannableStringBuilder(username)
            checkUsername(username)
        }

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
            colorPickerDialogFragment.show(parentFragmentManager, "colorPickerDialog")
        }
        return binding.root
    }

    override fun onColorSelected(colorID: Int) {
        Log.d("userColor", "selected colour: $colorID")
        binding.colorButton.backgroundTintList = ContextCompat.getColorStateList(context!!, colorID)
        color = colorID
        storeColor(color)
    }

    private fun checkUsername(name: String) {
        binding.roomHeaderText.visibility = View.VISIBLE
        if (isValidUsername(name)) {
            binding.usernameValidText.text = resources.getString(R.string.valid)
            binding.usernameValidText.setTextColor(ContextCompat.getColor(context!!, R.color.colorValid))
            binding.roomHeaderText.text = resources.getString(R.string.popular_rooms)
            binding.roomRecycler.visibility = View.VISIBLE
            binding.createRoomButton.visibility = View.VISIBLE
            username = name
            storeUser(User(username, color))
        } else {
            binding.usernameValidText.text = resources.getString(R.string.invalid)
            binding.usernameValidText.setTextColor(ContextCompat.getColor(context!!, R.color.colorInvalid))
            binding.roomHeaderText.text = resources.getString(R.string.no_user)
            binding.roomRecycler.visibility = View.INVISIBLE
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

    private fun storeColor(colorID: Int) {
        val preferenceEditor = sharedPref.edit()
        preferenceEditor?.putInt(getString(R.string.color_key), colorID)
        preferenceEditor?.apply()
    }

    private fun storeName(name: String) {
        val preferenceEditor = sharedPref.edit()
        preferenceEditor?.putString(getString(R.string.username_key), name)
        preferenceEditor?.apply()
    }

    private fun storeUser(user: User) {
        Log.d("username store", "Storing: ${user.name}")
        storeColor(user.color)
        storeName(user.name)
    }
}
