package com.jlbennett.syncsports


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jlbennett.syncsports.databinding.FragmentCreateRoomBinding
import org.json.JSONObject


class CreateRoomFragment : Fragment() {
    private lateinit var binding: FragmentCreateRoomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_room, container, false)

        binding.team1Entry.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (binding.team1Entry.text.isBlank() && binding.team2Entry.text.isBlank()) {
                    binding.roomNameText.text = ""
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(team1: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val team2 = binding.team2Entry.text
                val newText = "$team1 vs $team2"
                binding.roomNameText.text = newText
            }
        })

        binding.team2Entry.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (binding.team1Entry.text.isBlank() && binding.team2Entry.text.isBlank()) {
                    binding.roomNameText.text = ""
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(team2: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val team1 = binding.team1Entry.text
                val newText = "$team1 vs $team2"
                binding.roomNameText.text = newText
            }
        })

        binding.submitButton.setOnClickListener {
            if (binding.team1Entry.text.isNotBlank() && binding.team2Entry.text.isNotBlank()) {
                postNewRoom(binding.roomNameText.text.toString())
            } else {
                val toast = Toast.makeText(context, "Please enter a valid room name", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP, 0, 128)
                toast.show()
            }
        }

        return binding.root
    }

    private fun postNewRoom(roomName: String) {
        val queue = Volley.newRequestQueue(activity)
        val urlString = "http://syncsport.herokuapp.com/rooms/$roomName".replace(" ", "%20")
        Log.d("POST", "POSTing room: $roomName")
        val stringRequest = StringRequest(
            Request.Method.POST, urlString,
            Response.Listener<String> { response ->
                val jsonObject = JSONObject(response)
                val returnedRoom = jsonObject.getString("roomName")
                Log.d("POST", "Response is: $returnedRoom")
                val toast = Toast.makeText(context, "Creating new room: $returnedRoom", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP, 0, 128)
                toast.show()
                val action = CreateRoomFragmentDirections.actionCreateRoomFragmentToSyncFragment(returnedRoom)
                findNavController().navigate(action)
            },
            Response.ErrorListener { error ->
                Log.d("POST", "HTTP Error: $error")
                error.printStackTrace()
                Toast.makeText(context, "Error creating new room", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(stringRequest)
    }
}
