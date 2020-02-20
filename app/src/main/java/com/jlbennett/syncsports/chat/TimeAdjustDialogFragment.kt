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

    var isShown: Boolean = false

    private lateinit var newTimeText: TextView
    private var newTime = MatchTime(State.PRE_MATCH, 0, 0, 0)
    private var adjustmentSeconds = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isShown = true
        val view = inflater.inflate(R.layout.time_adjust, container, false)
        view.findViewById<Button>(R.id.plus5_button).setOnClickListener { button -> setAdjustment(button) }
        view.findViewById<Button>(R.id.minus5_button).setOnClickListener { button -> setAdjustment(button) }
        newTimeText = view.findViewById(R.id.new_time_text)
        return view
    }

    private fun setAdjustment(button: View?) {
        adjustmentSeconds = when(button!!.id) {
            R.id.minus5_button -> adjustmentSeconds - 5
            R.id.plus5_button -> adjustmentSeconds + 5
            else -> 0
        }
        Log.d("AdjustLog", "setAdjust called: ${button.id}")
    }

    fun updateTime(matchTime: MatchTime) {
        Log.d("AdjustLog", "updateTime called: ${matchTime.seconds}")
        newTime = matchTime
        val minString = String.format("%02d", newTime.minutes + ((newTime.seconds + adjustmentSeconds) / 60))
        val secString = String.format("%02d", (newTime.seconds + adjustmentSeconds) % 60)
        val matchTimeString = "New time — $minString:$secString"
        newTimeText.text = matchTimeString
    }

    override fun onDestroyView() {
        Log.d("AdjustLog", "onDestroyView")
        isShown = false
        super.onDestroyView()
    }
}