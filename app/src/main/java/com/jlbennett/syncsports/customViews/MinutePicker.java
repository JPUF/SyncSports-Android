package com.jlbennett.syncsports.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

public class MinutePicker extends NumberPicker {
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
        setMinValue(0);
        setMaxValue(50);
        setWrapSelectorWheel(true);
        setValue(25);
    }
}
