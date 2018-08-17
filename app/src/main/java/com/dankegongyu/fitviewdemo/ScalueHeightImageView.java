package com.dankegongyu.fitviewdemo;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 保持图片比例，高变化
 */
public class ScalueHeightImageView extends android.support.v7.widget.AppCompatImageView {
    public ScalueHeightImageView(Context context) {
        super(context);
        setScaleType(ScaleType.FIT_XY);
    }

    public ScalueHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.FIT_XY);
    }

    public ScalueHeightImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(ScaleType.FIT_XY);
    }
}
