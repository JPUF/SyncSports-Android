package com.jlbennett.syncsports.home

import android.os.Bundle
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
        val chosenColorID : Int = when (button!!.id) {
            R.id.blue_button -> R.color.blue
            R.id.red_button -> R.color.red
            R.id.orange_button -> R.color.orange
            R.id.yellow_button -> R.color.yellow
            R.id.green_button -> R.color.green
            R.id.purple_button -> R.color.purple
            else -> R.color.blue
        }

        val listener = targetFragment!! as DialogListener
        listener.onColorSelected(chosenColorID)

        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        dismiss()
    }

    interface DialogListener {
        fun onColorSelected(colorID: Int)
    }
}