package com.dankegongyu.fitviewdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * 保持图片宽高比，宽变化
 */
public class ScaleWidthImageView extends android.support.v7.widget.AppCompatImageView {
    public ScaleWidthImageView(Context context) {
        super(context);
        setScaleType(ScaleType.FIT_XY);
    }

    public ScaleWidthImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.FIT_XY);
    }

    public ScaleWidthImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(ScaleType.FIT_XY);
    }
}
