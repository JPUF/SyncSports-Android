package com.jlbennett.syncsports.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

public class SecondPicker extends NumberPicker {
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
        setMinValue(0);
        setMaxValue(59);
        setWrapSelectorWheel(true);
        setValue(30);
    }
}
