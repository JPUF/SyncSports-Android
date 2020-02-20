package com.jlbennett.syncsports.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.jlbennett.syncsports.R

class TimeAdjustDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.time_adjust, container, false)
        view.findViewById<Button>(R.id.plus5_button).setOnClickListener { button-> setAdjustment(button) }
        view.findViewById<Button>(R.id.minus5_button).setOnClickListener { button-> setAdjustment(button) }
        return view
    }

    private fun setAdjustment(button: View?) {
        Log.d("AdjustLog", "setAdjust called: ${button!!.id}")
    }
}