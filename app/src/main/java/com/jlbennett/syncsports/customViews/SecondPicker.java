package com.jlbennett.syncsports.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

public class SecondPicker extends NumberPicker {

    public static final String[] SECONDS = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
            "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
            "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45",
            "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};

    public SecondPicker(Context context) {
        super(context);
        init();
    }

    public SecondPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SecondPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setDisplayedValues(SECONDS);
        setMinValue(0);
        setMaxValue(SECONDS.length - 1);
        setWrapSelectorWheel(true);
    }
}
