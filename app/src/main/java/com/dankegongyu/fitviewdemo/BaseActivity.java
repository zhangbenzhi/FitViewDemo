package com.dankegongyu.fitviewdemo;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

public class BaseActivity extends FragmentActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        fitView();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        fitView();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        fitView();
    }

    private void fitView() {
        //屏幕适配：
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        if (viewGroup != null && viewGroup.getChildAt(0) instanceof ViewGroup) {
            ViewGroup child_viewgroup = (ViewGroup) viewGroup.getChildAt(0);
            FitViewUtil.scaleContentView(child_viewgroup);
        }
    }


}
