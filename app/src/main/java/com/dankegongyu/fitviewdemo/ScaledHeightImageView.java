package com.dankegongyu.fitviewdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * 宽度确定的情况下，保持原图比例，高针对宽适配
 *
 * @author wpq
 * @version 1.0
 */
public class ScaledHeightImageView extends AppCompatImageView {

    private int originalWidth, originalHeight;

    public ScaledHeightImageView(Context context) {
        super(context);
        setScaleType(ScaleType.FIT_XY);
    }

    public ScaledHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.FIT_XY);

        @SuppressLint("CustomViewStyleable")
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScaledImageView);
        try {
            originalWidth = ta.getInteger(R.styleable.ScaledImageView_original_width, 1);
            originalHeight = ta.getInteger(R.styleable.ScaledImageView_original_height, 1);
        } finally {
            ta.recycle();
        }
    }

    /**
     * 原图尺寸（px）
     *
     * @param originalWidth  原图宽（px）
     * @param originalHeight 原图高（px）
     */
    public void setOriginalSize(int originalWidth, int originalHeight) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
        invalidate();
    }

    public int getOriginalWidth() {
        return originalWidth;
    }

    public int getOriginalHeight() {
        return originalHeight;
    }
}
