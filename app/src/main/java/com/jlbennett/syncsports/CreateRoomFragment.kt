package com.jlbennett.syncsports


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jlbennett.syncsports.databinding.FragmentCreateRoomBinding


class CreateRoomFragment : Fragment() {
    private lateinit var binding: FragmentCreateRoomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_room, container, false)

        binding.team1Entry.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(binding.team1Entry.text.isBlank() && binding.team2Entry.text.isBlank()){
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

        binding.team2Entry.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(binding.team1Entry.text.isBlank() && binding.team2Entry.text.isBlank()){
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
            if(binding.team1Entry.text.isNotBlank() && binding.team2Entry.text.isNotBlank()){
                //TODO navigate to sync fragment.
            } else {
                Toast.makeText(context, "Please enter a valid room name", Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }


}
