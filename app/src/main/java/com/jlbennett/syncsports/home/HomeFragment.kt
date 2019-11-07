package com.jlbennett.syncsports.home


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.jlbennett.syncsports.R
import com.jlbennett.syncsports.databinding.FragmentHomeBinding
import org.w3c.dom.Text
import java.util.regex.Pattern


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)

        binding.dummyCard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_syncFragment)
        }

        binding.usernameEntry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val username = p0.toString().trim()//removes leading and trailing whitespace.
                if(isValidUsername(username)) {
                    binding.usernameValidText.text = resources.getString(R.string.valid)
                    binding.usernameValidText.setTextColor(ContextCompat.getColor(context!!, R.color.colorValid))
                    binding.roomHeaderText.visibility = View.VISIBLE
                    binding.roomScroll.visibility = View.VISIBLE
                } else{
                    binding.usernameValidText.text = resources.getString(R.string.invalid)
                    binding.usernameValidText.setTextColor(ContextCompat.getColor(context!!, R.color.colorInvalid))
                    binding.roomHeaderText.visibility = View.INVISIBLE
                    binding.roomScroll.visibility = View.INVISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        return binding.root
    }

    private fun isValidUsername(username: String): Boolean {
        val regexPattern = Pattern.compile("[^a-zA-Z0-9]")
        if(username.length <= 3 || username.length >= 10)
            return false
        else if(username.contains(' '))
            return false
        else if(regexPattern.matcher(username).find())
            return false

        return true//returns true (valid) if previous checks are passed
    }


}
