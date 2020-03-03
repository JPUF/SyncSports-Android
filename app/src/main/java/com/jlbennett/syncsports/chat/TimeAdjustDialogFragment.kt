package com.jlbennett.syncsports.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.jlbennett.syncsports.R
import com.jlbennett.syncsports.util.MatchTime
import com.jlbennett.syncsports.util.State

class TimeAdjustDialogFragment : DialogFragment() {

    var dialogIsShown: Boolean = false
    var timeIsUpdated: Boolean = false

    private lateinit var newTimeText: TextView
    private var currentTime = MatchTime(State.PRE_MATCH, 0, 0, 0)
    private var adjustedTime = MatchTime(State.PRE_MATCH, 0, 0, 0)
    private var adjustmentSeconds = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialogIsShown = true
        val view = inflater.inflate(R.layout.time_adjust, container, false)
        view.findViewById<Button>(R.id.plus1_button).setOnClickListener { setAdjustment(R.id.plus1_button) }
        view.findViewById<Button>(R.id.plus5_button).setOnClickListener { setAdjustment(R.id.plus5_button) }
        view.findViewById<Button>(R.id.minus1_button).setOnClickListener { setAdjustment(R.id.minus1_button) }
        view.findViewById<Button>(R.id.minus5_button).setOnClickListener { setAdjustment(R.id.minus5_button) }
        view.findViewById<Button>(R.id.set_button).setOnClickListener { timeSet() }
        newTimeText = view.findViewById(R.id.new_time_text)
        return view
    }

    private fun setAdjustment(id: Int) {
        adjustmentSeconds = when (id) {
            R.id.minus1_button -> adjustmentSeconds - 1
            R.id.minus5_button -> adjustmentSeconds - 5
            R.id.plus1_button -> adjustmentSeconds + 1
            R.id.plus5_button -> adjustmentSeconds + 5
            else -> 0
        }
        updateTime()
    }

    fun updateTime(matchTime: MatchTime? = null) {
        if (matchTime != null) {
            currentTime = matchTime
        }
        adjustedTime = MatchTime(
            currentTime.state,
            currentTime.minutes + ((currentTime.seconds + adjustmentSeconds) / 60),
            (currentTime.seconds + adjustmentSeconds) % 60,
            currentTime.quarterSeconds
        )
        //TODO needs to wrap going backwards over the minute mark.. (no minus seconds)
        Log.d("timeAdjust", "seconds: $adjustmentSeconds")
        val minString = String.format("%02d", adjustedTime.minutes)
        val secString = String.format("%02d", adjustedTime.seconds)
        val matchTimeString = "New time — $minString:$secString"
        newTimeText.text = matchTimeString
        timeIsUpdated = true
    }

    private fun timeSet() {
        val listener = targetFragment!! as DialogListener
        listener.onTimeSet(adjustedTime)
        dismiss()
    }

    override fun onDestroyView() {
        dialogIsShown = false
        timeIsUpdated = false
        super.onDestroyView()
        adjustmentSeconds = 0
        dismiss()
    }

    interface DialogListener {
        fun onTimeSet(matchTime: MatchTime)
    }
}