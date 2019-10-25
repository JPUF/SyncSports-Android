package com.jlbennett.syncsports


import android.os.Bundle
import android.util.Log
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

        binding.chatButton.setOnClickListener {
            var minutes = 0
            var seconds = 0
            when(binding.halfPicker.value) {
                1 -> {
                    minutes = binding.minutePicker.value
                    seconds = binding.secondPicker.value
                }
                3 -> {
                    minutes = (binding.minutePicker.value + 45)
                    seconds = binding.secondPicker.value
                }
            }
            val matchState = when(binding.halfPicker.value){
                0 -> State.PRE_MATCH
                1 -> State.FIRST_HALF
                2 -> State.HALF_TIME
                3 -> State.SECOND_HALF
                4 -> State.FULL_TIME
                else -> State.PRE_MATCH
            }
            val time = MatchTime(matchState, minutes, seconds)

            val action = SyncFragmentDirections.actionSyncFragmentToChatFragment(time)
            findNavController().navigate(action)
        }

        return binding.root
    }

}
