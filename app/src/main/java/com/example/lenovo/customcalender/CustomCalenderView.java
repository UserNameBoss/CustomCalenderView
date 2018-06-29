package com.example.lenovo.customcalender;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.Calendar;

/**
 * Created by lenovo on 2018/6/29 029.
 */

public class CustomCalenderView extends SurfaceView {

    public CustomCalenderView(Context context) {
        super(context);
    }

    public CustomCalenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCalenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 设置和年份月份
     */
    public void setYearAndMonth(int year,int mount){
        Calendar calendar=Calendar.getInstance();

    }


}
