package com.jlbennett.syncsports.customViews;

import android.content.Context;
import android.util.AttributeSet;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class HalfPicker extends NumberPickerView {
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
        setWrapSelectorWheel(false);
        final String[] matchStates = {"Pre-Match", "1st Half", "Half Time", "2nd Half", "Full Time"};
        setDisplayedValues(matchStates);
        setMinValue(0);
        setMaxValue(matchStates.length - 1);
        setFriction(0.03f);
    }
}
