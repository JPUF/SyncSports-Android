package com.jlbennett.syncsports.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

public class HalfPicker extends NumberPicker {
    public HalfPicker(Context context) {
        super(context);
        init();
    }

    public HalfPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HalfPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setMinValue(0);
        setMaxValue(1);
        setWrapSelectorWheel(false);
        final String[] halves = {"1st Half", "2nd Half"};
        setDisplayedValues(halves);
    }
}
