package com.example.lenovo.customcalender;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

/**
 * 显示年月日标题和星期几
 * Created by lenovo on 2018/7/6 006.
 */

public class CustomTitleView extends View {
    //所选择的月份和年份
    private int selectMonth=-1;
    private int selectYear=2018;


    //显示年月日标题的高度
    private float yearMonthTitleHeight=100f;
    //年月标题的背景颜色
    private int yearMonthBgColor=0;
    //年月标题的字体颜色
    private int yearMonthTextColor=0;
    //年月标题的字体
    private float yearMonthTextSize=50f;

    //星期几的行宽
    private float weekRowHeight=150f;
    //星期几的字体大小
    private float weekTextsize=50f;
    //星期几的字体颜色
    private int weekTextColor=0;

    //绘制背景
    private Paint bgPaint;
    //绘制年月标题
    private Paint yearMonthPaint;
    //绘制星期几
    private Paint weekPaint;

    private TypedArray typedArray;


    public CustomTitleView(Context context) {
        super(context);
        initView();

    }

    public CustomTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initType(attrs);
        initView();

    }

    public CustomTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initType(attrs);
        initView();

    }

    public CustomTitleView(Context context, TypedArray typedArray) {
        super(context);
        initType(typedArray);
        initView();

    }

    public void initType(AttributeSet attrs){
        typedArray=this.getContext().obtainStyledAttributes(attrs,R.styleable.CustomCalenderView);
        initType(typedArray);
        initView();
    }

    @SuppressLint("ResourceAsColor")
    public void initType(TypedArray typedArray){
        this.typedArray=typedArray;
        yearMonthBgColor=typedArray.getColor(R.styleable.CustomCalenderView_ccv_yearMonthBgColor,R.color.white);
        yearMonthTextColor=typedArray.getColor(R.styleable.CustomCalenderView_ccv_yearMonthTextColor,R.color.black);
        weekTextColor=typedArray.getColor(R.styleable.CustomCalenderView_ccv_weekTextColor,R.color.black);
    }

    public void initView(){
        Calendar calendar=Calendar.getInstance();
        selectYear=calendar.get(Calendar.YEAR);
        selectMonth=calendar.get(Calendar.MONTH)+1;
        SaveDateInfoUtils.clickYear=selectYear;
        SaveDateInfoUtils.clickMonth=selectMonth;
        SaveDateInfoUtils.clickDay=calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bgPaint=new Paint();
        yearMonthPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        yearMonthPaint.setTextAlign(Paint.Align.CENTER);
        yearMonthPaint.setColor(yearMonthTextColor);
        yearMonthPaint.setTextSize(yearMonthTextSize);


        weekPaint=new Paint();
        weekPaint.setColor(weekTextColor);
        weekPaint.setTextSize(weekTextsize);
        weekPaint.setTextAlign(Paint.Align.CENTER);
        drawYearMonthTitle(canvas);
        drawWeek(canvas);
    }

    /**
     * 绘制年月标题
     */
    public void drawYearMonthTitle(Canvas canvas){
        //绘制背景
        bgPaint.setColor(yearMonthBgColor);
        RectF rectF=new RectF(0,0,getWidth(),yearMonthTitleHeight);
        canvas.drawRect(rectF,bgPaint);
        //绘制标题

        canvas.drawText(getTitle(selectYear,selectMonth),getWidth()/2,yearMonthTitleHeight/2+yearMonthTextSize/2,yearMonthPaint);


    }

    /**
     * 绘制星期几的标题
     */
    public void drawWeek(Canvas canvas){
        int weekOneWidth=getWidth()/7;

        for(int i=0;i<7;i++){
            String text="周日";
            switch (i){
                case 0:
                    text="周日";
                    break;
                case 1:
                    text="周一";
                    break;
                case 2:
                    text="周二";
                    break;
                case 3:
                    text="周三";
                    break;
                case 4:
                    text="周四";
                    break;
                case 5:
                    text="周五";
                    break;
                case 6:
                    text="周六";
                    break;
            }

            canvas.drawText(text,i*weekOneWidth+weekOneWidth/2,yearMonthTitleHeight+weekRowHeight/2,weekPaint);
        }
    }

    public void setYearAndMonth(int selectYear,int selectMonth){
        this.selectMonth = selectMonth;
        this.selectYear = selectYear;
        invalidate(0,0,getWidth(), (int) yearMonthTitleHeight);
    }

    /**
     * 获取标题
     */
    public String getTitle(int year,int month){
        return year+"年"+(month>=10?month:"0"+month)+"月";
    }

}
