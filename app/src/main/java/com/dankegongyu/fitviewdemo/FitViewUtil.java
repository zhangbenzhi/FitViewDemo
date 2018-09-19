package com.dankegongyu.fitviewdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import static com.dankegongyu.fitviewdemo.BaseMyConfigConstant.UI_HEIGHT;
import static com.dankegongyu.fitviewdemo.BaseMyConfigConstant.UI_PER_DIP;
import static com.dankegongyu.fitviewdemo.BaseMyConfigConstant.UI_WIDTH;


/**
 * 屏幕适配：布局缩放工具类：
 */

public class FitViewUtil {
    /**
     * 无效值
     */
    public static final int INVALID = Integer.MIN_VALUE;

    /**
     * 类型：TYPE_X代表：横向 ；TYPE_Y代表：纵向 ；TYPE_SIZE代表：字体；
     */
    public static final int TYPE_X = 0;
    public static final int TYPE_Y = 1;
    public static final int TYPE_SIZE = 2;

    /**
     * 获得这个View的宽度
     */
    public static int getViewWidth(View view) {
        if (view.getMeasuredWidth() == 0) {
            measureView(view);
        }
        return view.getMeasuredWidth();
    }

    /**
     * 获得这个View的高度
     */
    public static int getViewHeight(View view) {
        if (view.getMeasuredHeight() == 0) {
            measureView(view);
        }
        return view.getMeasuredHeight();
    }

    /**
     * 描述：根据屏幕大小缩放.
     *
     * @param context
     * @param value   未缩放前的像素值；
     * @param type    横向/纵向/字体 的类型；
     * @return int
     */
    private static int scaleValue(Context context, float value, int type) {
        DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
        return scale(mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels,
                value, type);
    }


    /**
     * 缩放字体
     *
     * @param context
     * @param value   缩放前的像素值；
     * @return 缩放后的字体像素值；
     */
    public static int scaleTextValue(Context context, float value) {
        return scaleValue(context, value, TYPE_SIZE);
    }

    /**
     * 根据屏幕宽缩放
     *
     * @param context
     * @param value
     * @return
     */
    public static int scaleWidthValue(Context context, float value) {
        return scaleValue(context, value, TYPE_X);
    }

    /**
     * 根据屏幕高缩放
     *
     * @param context
     * @param value
     * @return
     */
    public static int scaleHeigthValue(Context context, float value) {
        return scaleValue(context, value, TYPE_Y);
    }

    /**
     * 描述：根据屏幕大小缩放.
     *
     * @param displayWidth  測量的屏幕橫向像素值；
     * @param displayHeight 測量的屏幕纵向像素值；
     * @param pxValue       要缩放的像素值；
     * @param type          横向/纵向/字体 的类型；
     * @return int 缩放后的像素值；
     */
    private static int scale(int displayWidth, int displayHeight, float pxValue,
                             int type) {
        if (pxValue == 0) {
            return 0;
        }
        float width = displayWidth;
        float height = displayHeight;

        /*float min_size = Math.min(width, height);
        float min_base_size = Math.min(UI_WIDTH, UI_HEIGHT);*/

        float scaleWidth = width / UI_WIDTH;
        float scaleHeight = height / UI_HEIGHT;

        double displayDiagonal = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));
        double designDiagonal = Math.sqrt(Math.pow(UI_WIDTH, 2) + Math.pow(UI_HEIGHT, 2));
        float scaleSize = (float) (displayDiagonal / designDiagonal);

        // 横向：
        if (type == TYPE_X) {
            return Math.round(pxValue * scaleWidth + 0.5f);
            // 竖向：
        } else if (type == TYPE_Y) {
            return Math.round(pxValue * scaleHeight + 0.5f);
            // 字体：
        } else {
            return Math.round(pxValue * scaleSize + 0.5f);
        }
    }

    /**
     * @param view 是否需要缩放；此时返回true，如果有不需要缩放的情况再添加条件；
     * @return
     */
    public static boolean isNeedScale(View view) {
        return true;
    }

    /**
     * 对ViewGroup及包裹的所有内容进行缩放：
     *
     * @param contentView
     */
    public static void scaleContentView(ViewGroup contentView) {
        if (contentView == null) {
            return;
        }
        FitViewUtil.scaleView(contentView);
        if (contentView.getChildCount() > 0) {
            for (int i = 0; i < contentView.getChildCount(); i++) {
                View view = contentView.getChildAt(i);
                if (view instanceof ViewGroup) {
                    scaleContentView((ViewGroup) (view));
                } else {
                    scaleView(contentView.getChildAt(i));
                }
            }
        }
    }

    /**
     * 缩放单个view;
     *
     * @param view
     */
    public static void scaleView(View view) {
        if (!isNeedScale(view)) {
            return;
        }
        //适配TextView字体
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            setTextSize(textView, textView.getTextSize());
        }
        //适配View宽高
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (null != params) {
            int width = INVALID;
            int height = INVALID;
            if (params.width != ViewGroup.LayoutParams.WRAP_CONTENT
                    && params.width != ViewGroup.LayoutParams.MATCH_PARENT) {
                width = params.width;
            }
            if (params.height != ViewGroup.LayoutParams.WRAP_CONTENT
                    && params.height != ViewGroup.LayoutParams.MATCH_PARENT) {
                height = params.height;
            }
            setViewSize(view, width, height);
        }
        // Padding
        setPadding(view, view.getPaddingLeft(), view.getPaddingTop(),
                view.getPaddingRight(), view.getPaddingBottom());
        // Margin
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams mMarginLayoutParams = (ViewGroup.MarginLayoutParams) view
                    .getLayoutParams();
            if (mMarginLayoutParams != null) {
                setMargin(view, mMarginLayoutParams.leftMargin,
                        mMarginLayoutParams.topMargin,
                        mMarginLayoutParams.rightMargin,
                        mMarginLayoutParams.bottomMargin);
            }
        }
        //适配最小宽高
        setMaxAndMinWidthAndHeight(view.getContext(), view);
    }

    /**
     * 将缩放后字体大小值设置到TextView上；
     *
     * @param textView
     * @param sizePixels 缩放前的字体像素值；
     * @return
     */
    public static void setTextSize(TextView textView, float sizePixels) {
        float scaledSize = scaleTextValue(textView.getContext(), sizePixels);
        textView.setIncludeFontPadding(false);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledSize);
    }

    /**
     * 适配最大最小宽度和高度；
     *
     * @return
     */
    public static void setMaxAndMinWidthAndHeight(Context context, View view) {
        if (context == null || view == null) {
            return;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                int maxWidth = tv.getMaxWidth();
                int maxHeight = tv.getMaxHeight();
                if (maxWidth > 0) {
                    tv.setMaxWidth(FitViewUtil.scaleValue(context, maxWidth, TYPE_X));
                }
                if (maxHeight > 0) {
                    tv.setMaxHeight(FitViewUtil.scaleValue(context, maxHeight, TYPE_Y));
                }
            }
            int minimumWidth = view.getMinimumWidth();
            int minimumHeight = view.getMinimumHeight();
            if (minimumWidth > 0) {
                view.setMinimumWidth(FitViewUtil.scaleWidthValue(context, minimumWidth));
            }
            if (minimumHeight > 0) {
                view.setMinimumHeight(FitViewUtil.scaleHeigthValue(context, minimumHeight));
            }
        }
    }


    /**
     * 缩放画笔对象字体大小 用于自定义view;
     *
     * @param context
     * @param paint
     * @param sizePixels
     */
    public static void setTextSize(Context context, Paint paint,
                                   float sizePixels) {
        float scaledSize = scaleTextValue(context, sizePixels);
        paint.setTextSize(scaledSize);
    }

    /**
     * 设置View的PX尺寸
     *
     * @param view         如果是代码new出来的View，需要设置一个适合的LayoutParams
     * @param widthPixels
     * @param heightPixels
     */
    public static void setViewSize(View view, int widthPixels, int heightPixels) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            return;
        }
        if (view instanceof ScaleWidthImageView) {
            heightPixels = getViewHeight(view);
            int scaleValue = scaleHeigthValue(view.getContext(), heightPixels);
            params.height = scaleValue;
            params.width = scaleValue;
        } else if (view instanceof ScalueHeightImageView) {
            widthPixels = getViewWidth(view);
            int scaleValue = scaleWidthValue(view.getContext(), widthPixels);
            params.width = scaleValue;
            params.height = scaleValue;
        } else {
            if (widthPixels != INVALID) {
                params.width = scaleWidthValue(view.getContext(), widthPixels);
            }
            if (heightPixels != INVALID) {
                params.height = scaleHeigthValue(view.getContext(), heightPixels);
            }
        }
        view.setLayoutParams(params);
    }

    /**
     * 设置PX padding.
     *
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setPadding(View view, int left, int top, int right,
                                  int bottom) {
        int scaledLeft = scaleWidthValue(view.getContext(), left);
        int scaledTop = scaleHeigthValue(view.getContext(), top);
        int scaledRight = scaleWidthValue(view.getContext(), right);
        int scaledBottom = scaleHeigthValue(view.getContext(), bottom);
        view.setPadding(scaledLeft, scaledTop, scaledRight, scaledBottom);
    }

    /**
     * 设置 PX margin.
     *
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setMargin(View view, int left, int top, int right,
                                 int bottom) {
        int scaledLeft = scaleValue(view.getContext(), left, TYPE_X);
        int scaledTop = scaleValue(view.getContext(), top, TYPE_Y);
        int scaledRight = scaleValue(view.getContext(), right, TYPE_X);
        int scaledBottom = scaleValue(view.getContext(), bottom, TYPE_Y);
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams mMarginLayoutParams = (ViewGroup.MarginLayoutParams) view
                    .getLayoutParams();
            if (mMarginLayoutParams != null) {
                if (left != INVALID) {
                    mMarginLayoutParams.leftMargin = scaledLeft;
                }
                if (right != INVALID) {
                    mMarginLayoutParams.rightMargin = scaledRight;
                }
                if (top != INVALID) {
                    mMarginLayoutParams.topMargin = scaledTop;
                }
                if (bottom != INVALID) {
                    mMarginLayoutParams.bottomMargin = scaledBottom;
                }
                view.setLayoutParams(mMarginLayoutParams);
            }
        }
    }

    /**
     * 获取请求图片的宽：考虑到像素密度的情况；
     *
     * @param view
     * @return
     */
    public static int getRequestImageWidth(View view) {
        int width = getViewWidth(view);
        try {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams != null) {
                if (layoutParams.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                    ViewParent parent = view.getParent();
                    if (parent != null && parent instanceof ViewGroup) {
                        ViewGroup viewGroup = (ViewGroup) parent;
                        ViewGroup.LayoutParams layoutParams1 = viewGroup.getLayoutParams();
                        width = layoutParams1.width;
                    }
                } else if (layoutParams.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
                    width = layoutParams.width == 0 ? width : layoutParams.width;
                }
            }
        } catch (Exception e) {
        }
        /*int per_dpi = getDisplayMetrics(Application.getInstance()).densityDpi;
        float scale = (float) MyConfigConstant.UI_PER_DIP / per_dpi;
        scale = scale == 0 ? 1 : scale;*/
        width = (int) (width * 1.0);
        return width;
    }

    /**
     * 获取请求图片的宽：考虑到像素密度的情况；
     *
     * @return
     */
    public static int getRequestImageWidth(Context context, int width) {
        int per_dpi = getDisplayMetrics(context).densityDpi;
        float scale = (float) UI_PER_DIP / per_dpi;
        scale = scale == 0 ? 1 : scale;
        width = (int) (width * scale);
        return width;
    }

    /**
     * 获取请求图片的高：考虑到像素密度的情况；
     *
     * @param view
     * @return
     */
    public static int getRequestImageHeight(View view) {
        int height = getViewHeight(view);
        try {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams != null) {
                if (layoutParams.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                    ViewParent parent = view.getParent();
                    if (parent != null && parent instanceof ViewGroup) {
                        ViewGroup viewGroup = (ViewGroup) parent;
                        ViewGroup.LayoutParams layoutParams1 = viewGroup.getLayoutParams();
                        height = layoutParams1.height;
                    }
                } else if (layoutParams.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
                    height = layoutParams.height == 0 ? height : layoutParams.height;
                }
            }
        } catch (Exception e) {
        }
        /*int per_dpi = getDisplayMetrics(Application.getInstance()).densityDpi;
        float scale = (float) MyConfigConstant.UI_PER_DIP / per_dpi;
        scale = scale == 0 ? 1 : scale;*/
        height = (int) (height * 1.0);
        return height;
    }

    /**
     * @return
     */
    public static int getRequestImageHeight(Context context, int height) {
        int per_dpi = getDisplayMetrics(context).densityDpi;
        float scale = (float) UI_PER_DIP / per_dpi;
        scale = scale == 0 ? 1 : scale;
        height = (int) (height * scale);
        return height;
    }

    /**
     * 获取屏幕尺寸与密度.
     *
     * @param context
     * @return mDisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context == null) {
            mResources = Resources.getSystem();
        } else {
            mResources = context.getResources();
        }
        return mResources.getDisplayMetrics();
    }

    /**
     * 测量这个view 最后通过getMeasuredWidth()获取宽度和高度.
     *
     * @param view 要测量的view
     * @return 测量过的view
     */
    private static void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        view.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * TypedValue官方源码中的算法，任意单位转换为PX单位
     *
     * @param unit    TypedValue.COMPLEX_UNIT_DIP
     * @param value   对应单位的值
     * @param metrics 密度
     * @return px值
     */
    public static float applyDimension(int unit, float value,
                                       DisplayMetrics metrics) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }
}
