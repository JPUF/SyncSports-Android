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
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(team1: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val team2 = binding.team2Entry.text
                val newText = "$team1 vs $team2"
                binding.roomNameText.text = newText
            }
        })

        binding.team2Entry.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(team2: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val team1 = binding.team1Entry.text
                val newText = "$team1 vs $team2"
                binding.roomNameText.text = newText
            }
        })

        binding.submitButton.setOnClickListener {
            val teams = binding.roomNameText.text.toString().replace(" vs ","")
            if(teams.isNotBlank()){
                //TODO navigate to sync fragment.
                //further sanity checks (both teams are present)
            } else {
                Toast.makeText(context, "Please enter a valid room name", Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }


}
