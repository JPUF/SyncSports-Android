package com.jlbennett.syncsports.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.jlbennett.syncsports.R

class ColorPickerDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.color_picker, container, false)

        val button1 = view.findViewById<Button>(R.id.blue_button)
        button1.setOnClickListener {
            Log.d("HomeFragment Log", "fragment button: dismissing")
            dismiss()
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeFragment Log", "fragment onDestroy: dismissing")
        dismiss()
    }
}