package com.example.lenovo.customcalender;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by lenovo on 2018/7/6 006.
 */

public class CustomUseCalenderView extends LinearLayout {

    private TypedArray typedArray;
    private CustomTitleView customTitleView;
    private CustomViewPager customViewPager;


    public CustomUseCalenderView(Context context) {
        super(context);
        initView();
    }

    public CustomUseCalenderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initType(attrs);
        initView();
    }

    public CustomUseCalenderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initType(attrs);
        initView();
    }

    public void initType(AttributeSet attrs){
        typedArray=this.getContext().obtainStyledAttributes(attrs,R.styleable.CustomCalenderView);
    }

    public void initView(){
        //绘制标题
        customTitleView=new CustomTitleView(this.getContext(),typedArray);
        LayoutParams layoutParams=new LayoutParams(LayoutParams.MATCH_PARENT,200);
        this.addView(customTitleView,layoutParams);
        //绘制日期
        customViewPager=new CustomViewPager(this.getContext(),typedArray);
        customViewPager.setChangeMonthLinsenter(new CustomViewPager.ChangeMonthLinsenter() {
            @Override
            public void setYearAndMonth(int year, int month) {
                customTitleView.setYearAndMonth(year,month);
            }
        });
        this.addView(customViewPager);

    }
}
