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

        view.findViewById<Button>(R.id.blue_button).setOnClickListener { button -> setColor(button) }
        view.findViewById<Button>(R.id.red_button).setOnClickListener { button -> setColor(button) }
        view.findViewById<Button>(R.id.orange_button).setOnClickListener { button -> setColor(button) }
        view.findViewById<Button>(R.id.yellow_button).setOnClickListener { button -> setColor(button) }
        view.findViewById<Button>(R.id.green_button).setOnClickListener { button -> setColor(button) }
        view.findViewById<Button>(R.id.purple_button).setOnClickListener { button -> setColor(button) }
        return view
    }

    private fun setColor(button: View?) {
        val chosenColor : String = when (button!!.id) {
            R.id.blue_button -> "#0B4AB0"
            R.id.red_button -> "#910606"
            R.id.orange_button -> "#DE8100"
            R.id.yellow_button -> "#DBCA12"
            R.id.green_button -> "#13AD02"
            R.id.purple_button -> "#7D02AD"
            else -> "#0B4AB0"
        }

        val listener = targetFragment!! as DialogListener
        listener.onColorSelected(chosenColor)

        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeFragment Log", "fragment onDestroy: dismissing")
        dismiss()
    }

    interface DialogListener {
        fun onColorSelected(colorString: String)
    }
}