package com.example.lenovo.customcalender;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by lenovo on 2018/7/5 005.
 */

public class CustomViewPager extends ViewPager {

    private CustomPagerAdapter customPagerAdapter;
    private TypedArray typedArray;
    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTypedArray(attrs);
        initView();
    }

    public void initTypedArray(AttributeSet attributeSet){
        typedArray=this.getContext().obtainStyledAttributes(attributeSet, R.styleable.CustomCalenderView);
    }


    public void initView(){

        customPagerAdapter=new CustomPagerAdapter(this,typedArray,this.getContext());
        setAdapter(customPagerAdapter);
        setCurrentItem(customPagerAdapter.getMonthCount() / 2, false);
    }
}
