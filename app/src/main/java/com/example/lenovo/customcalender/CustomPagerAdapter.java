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

    public int getSelectYear() {
        return selectYear;
    }

    public int getSelectMonth() {
        return selectMonth;
    }

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
            getYearAndMonth(position);
            CustomCalenderView customCalenderView=new CustomCalenderView(selectYear,selectMonth,mContext,typedArray);
            ccvViews.put(position,customCalenderView);
        }
        container.addView(ccvViews.get(position));
        return ccvViews.get(position);
    }


    public SparseArray<CustomCalenderView> getCcvViews() {
        return ccvViews;
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


    /**
     * 计算对应页面下的年月
     */
    public void getYearAndMonth(int position){
        int current=maxMonthCount/2;
        int differMonth=Math.abs(position-current);
        System.out.println("============differMonth="+differMonth);
        if((position>current&&differMonth>12-currentMonth)||(position<current&&differMonth>=currentMonth)){
            int differYear=0;
            if(differMonth+currentMonth-12>0) {
                if(position>current){
                    differYear = (differMonth + currentMonth - 12) / 12 + ((differMonth + currentMonth  - 12) % 12 == 0 ? 0 : 1);
                }else {
                    differYear = (differMonth + currentMonth-1 - 12) / 12 + ((differMonth + currentMonth - 1 - 12) % 12 == 0 ? 0 : 1);
                }
            }
            if(position>current){
                selectYear=currentYear+differYear;
                selectMonth=(differMonth-(differYear-1)*12)-(12-currentMonth);

            }else{
                selectYear=currentYear-differYear;
                selectMonth=12-((differMonth-(differYear-1)*12)-currentMonth);
            }
        }else{
            if(position>current) {
                selectMonth = currentMonth + differMonth;
            }else{
                selectMonth = currentMonth - differMonth;
            }
            selectYear=currentYear;
        }
    }
}
