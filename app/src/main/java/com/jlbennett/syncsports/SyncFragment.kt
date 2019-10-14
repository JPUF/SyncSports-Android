package com.jlbennett.syncsports


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.jlbennett.syncsports.customViews.MinutePicker
import com.jlbennett.syncsports.databinding.FragmentSyncBinding


class SyncFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentSyncBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sync, container, false
        )

        binding.chatButton.setOnClickListener { findNavController().navigate(R.id.action_syncFragment_to_chatFragment) }

        binding.halfPicker.setOnValueChangedListener { _, _, selectedValue ->
            val visibility: Int = when (selectedValue) {
                1 -> View.VISIBLE//1 = 1st half
                3 -> View.VISIBLE//3 = 2nd half
                else -> View.INVISIBLE
            }
            binding.minutePicker.visibility = visibility
            binding.separatorText.visibility = visibility
            binding.secondPicker.visibility = visibility

            when (selectedValue) {
                1 -> {//1st Half
                    binding.minutePicker.minValue = 0
                    binding.minutePicker.maxValue = MinutePicker.FIRST_HALF_MINUTES.size - 1
                    binding.minutePicker.displayedValues = MinutePicker.FIRST_HALF_MINUTES
                }
                3 -> {//2nd Half
                    binding.minutePicker.minValue = 0
                    binding.minutePicker.maxValue = MinutePicker.SECOND_HALF_MINUTES.size - 1
                    binding.minutePicker.displayedValues = MinutePicker.SECOND_HALF_MINUTES
                }
            }
        }

        return binding.root
    }


}
