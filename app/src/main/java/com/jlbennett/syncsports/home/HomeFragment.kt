package com.jlbennett.syncsports.home


import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
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
import androidx.navigation.fragment.findNavController
import com.jlbennett.syncsports.R
import com.jlbennett.syncsports.databinding.FragmentHomeBinding
import java.util.regex.Pattern


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.dummyCard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_syncFragment)
        }

        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!
        val persistentUsername = sharedPref.getString(getString(R.string.username_key), "")
        binding.usernameEntry.text = SpannableStringBuilder(persistentUsername)
        checkUsername(persistentUsername!!)

        binding.usernameEntry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkUsername(p0.toString().trim())
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        val fragmentTransaction = fragmentManager!!.beginTransaction()
        val previousDialog = fragmentManager!!.findFragmentByTag("colorPickerDialog")
        if (previousDialog != null) fragmentTransaction.remove(previousDialog)
        val colorPickerDialogFragment = ColorPickerDialogFragment()
        fragmentTransaction.addToBackStack(null)

        binding.colorButton.setOnClickListener {
            fragmentManager!!.executePendingTransactions()
            //colorPickerDialogFragment.show(fragmentTransaction, "colorPickerDialog")
            fragmentTransaction.add(colorPickerDialogFragment, "colorPickerDialog")
            fragmentTransaction.commitAllowingStateLoss()
        }

        return binding.root
    }

    private fun checkUsername(username: String) {
        if (isValidUsername(username)) {
            binding.usernameValidText.text = resources.getString(R.string.valid)
            binding.usernameValidText.setTextColor(ContextCompat.getColor(context!!, R.color.colorValid))
            binding.roomHeaderText.visibility = View.VISIBLE
            binding.roomScroll.visibility = View.VISIBLE
            storeUsername(username)
        } else {
            binding.usernameValidText.text = resources.getString(R.string.invalid)
            binding.usernameValidText.setTextColor(ContextCompat.getColor(context!!, R.color.colorInvalid))
            binding.roomHeaderText.visibility = View.INVISIBLE
            binding.roomScroll.visibility = View.INVISIBLE
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

    private fun storeUsername(username: String) {
        val preferenceEditor = sharedPref.edit()
        preferenceEditor?.putString(getString(R.string.username_key), username)//TODO only call on navigate.
        preferenceEditor?.apply()
    }
}
