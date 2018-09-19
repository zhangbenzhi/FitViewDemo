package com.dankegongyu.fitviewdemo;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

public class BaseActivity extends FragmentActivity {

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        //屏幕适配：
        ViewGroup viewGroup = findViewById(android.R.id.content);
        if (viewGroup != null && viewGroup.getChildAt(0) instanceof ViewGroup) {
            ViewGroup child_viewgroup = (ViewGroup) viewGroup.getChildAt(0);
            FitViewUtil.scaleContentView(child_viewgroup);
        }
    }
}
