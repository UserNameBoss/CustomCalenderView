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
    private ChangeMonthLinsenter changeMonthLinsenter;

    public void setChangeMonthLinsenter(ChangeMonthLinsenter changeMonthLinsenter) {
        this.changeMonthLinsenter = changeMonthLinsenter;
    }

    public interface ChangeMonthLinsenter{
        void setYearAndMonth(int year,int month);
    }


    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTypedArray(attrs);
        initView();
    }


    public CustomViewPager(@NonNull Context context,TypedArray typedArray) {
        super(context);
        initTypedArray(typedArray);
        initView();
    }

    public void initTypedArray(AttributeSet attributeSet){
        typedArray=this.getContext().obtainStyledAttributes(attributeSet, R.styleable.CustomCalenderView);
    }

    public void initTypedArray(TypedArray typedArray){
        this.typedArray=typedArray;
    }


    public void initView(){

        customPagerAdapter=new CustomPagerAdapter(this, typedArray, this.getContext(), new OnClickEventListener() {
            @Override
            public void setYearMonthDay(int year, int month, int day) {
                System.out.println("======year="+year+"==month="+month+"==day="+day);
            }
        });
        setAdapter(customPagerAdapter);
        setCurrentItem(customPagerAdapter.getMonthCount() / 2, false);

        this.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CustomCalenderView customCalenderView=customPagerAdapter.getCcvViews().get(position);
                if(changeMonthLinsenter!=null&&customCalenderView!=null){


                    changeMonthLinsenter.setYearAndMonth(customCalenderView.getSelectYear(),customCalenderView.getSelectMonth());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
