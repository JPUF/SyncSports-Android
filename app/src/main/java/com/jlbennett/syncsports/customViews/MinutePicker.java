package com.jlbennett.syncsports.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.Arrays;


public class MinutePicker extends NumberPicker {

    public static final String[] FIRST_HALF_MINUTES = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
            "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
            "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45",
            "+1", "+2", "+3", "+4", "+5", "+6", "+7", "+8"};
    public static final String[] SECOND_HALF_MINUTES = {"45", "46", "47", "48", "49", "50", "51", "52", "53", "54",
            "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72",
            "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90",
            "+1", "+2", "+3", "+4", "+5", "+6", "+7", "+8"};

    public MinutePicker(Context context) {
        super(context);
        init();
    }

    public MinutePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MinutePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        setWrapSelectorWheel(true);
        setDisplayedValues(FIRST_HALF_MINUTES);
        setMinValue(0);
        setMaxValue(FIRST_HALF_MINUTES.length - 1);
    }
}
