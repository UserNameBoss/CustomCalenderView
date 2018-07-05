package com.example.lenovo.customcalender;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

/**
 * Created by lenovo on 2018/7/5 005.
 */

public class CustomPagerAdapter extends PagerAdapter {

    //最大显示多少个月份
    private int maxMonthCount;
    private TypedArray typedArray;
    private SparseArray<CustomCalenderView> ccvViews;
    private CustomViewPager customViewPager;
    private Context mContext;
    private int selectYear,selectMonth;
    private int currentYear;
    private int currentMonth;

    public CustomPagerAdapter(CustomViewPager customViewPager, TypedArray typedArray, Context mContext) {
        this.customViewPager=customViewPager;
        this.typedArray = typedArray;
        maxMonthCount=typedArray.getInt(R.styleable.CustomCalenderView_ccv_maxMonthCount,40);
        this.ccvViews =new SparseArray<>();
        this.mContext = mContext;

        Calendar calendar=Calendar.getInstance();
        currentYear=calendar.get(Calendar.YEAR);
        currentMonth=calendar.get(Calendar.MONTH)+1;
    }

    @Override
    public int getCount() {
        return maxMonthCount;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if(ccvViews.get(position)==null){
            CustomCalenderView customCalenderView=new CustomCalenderView(mContext,typedArray);
            ccvViews.put(position,customCalenderView);
        }
        container.addView(ccvViews.get(position));
        return ccvViews.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    public int getMonthCount() {
        return maxMonthCount;
    }


    public void getYearAndMonth(int position){
        int current=maxMonthCount/2;
        int differMonth=Math.abs(position-current);
        if(differMonth>12-current){
            int differYear=(differMonth+current-12)/2+(differMonth+current-12)%2;
            if(position>current){
                selectYear=currentYear+differYear;

            }else{
                selectYear=currentYear-differYear;

            }
        }else{
            selectMonth=currentMonth+differMonth;
            selectYear=currentYear;
        }
    }
}
