package com.bov.vitali.training.common.utils;

import android.content.Context;
import android.util.TypedValue;

public class PixelUtils {

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}