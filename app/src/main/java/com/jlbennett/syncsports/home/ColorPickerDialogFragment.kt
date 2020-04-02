package com.jlbennett.syncsports.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.jlbennett.syncsports.R

/**
 * The logic for the ColorPicker pop-up.
 *
 * As a DialogFragment, it appears above the underlying HomeFragment.
 */
class ColorPickerDialogFragment : DialogFragment() {

    /**
     * This inflates the view, and sets the onClickListener for each button within the DialogFragment.
     */
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

    /**
     * Send the chosen colour to the assigned listener.
     *
     * The listener is that which implements this class's interface, and is set to be the DialogFragment's target Fragment.
     */
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

        dismiss()//Stop showing the DialogFragment. Return user to underlying HomeFragment
    }

    override fun onDestroy() {
        super.onDestroy()
        dismiss()//Stop showing the DialogFragment. Return user to underlying HomeFragment
    }

    /**
     * Implementing this allows for communication between the DialogFragment and the HomeFragment.
     */
    interface DialogListener {
        fun onColorSelected(colorID: Int)
    }
}