package com.example.lenovo.customcalender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.widget.GridView;

import java.util.Calendar;

/**
 * Created by lenovo on 2018/6/29 029.
 */

public class CustomCalenderView extends SurfaceView {

    //所选择的月份和年份
    private int selectMonth=1;
    private int selectYear=2018;
    //所选择的月份有多少天
    private int selectMonthDayCount=30;
    //所选择的月份1号是周几
    private int firstweek=1;
    //所选择的月份要显示几行
    private int lineNum=5;
    //当前年份和当前月份
    private int currentYear,currentMonth;


    //显示年月日标题的高度
    private float yearMonthTitleHeight=30f;
    //年月标题的背景颜色
    private int yearMonthBgColor=0;
    //年月标题的字体颜色
    private int yearMonthTextColor=0;
    //年月标题的字体
    private float yearMonthTextSize=12f;

    //画笔相关
    //绘制背景
    private Paint bgPaint;
    //绘制年月标题
    private Paint yearMonthPaint;
    private CustomCalenderAdapter customCalenderAdapter;

    public CustomCalenderView(Context context) {
        super(context);
        initView();
    }

    public CustomCalenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
        initView();
    }

    public CustomCalenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs);
        initView();
    }


    public void initView(){
        Calendar calendar=Calendar.getInstance();
        currentYear=calendar.get(Calendar.YEAR);
        currentMonth=calendar.get(Calendar.MONTH)+1;
        selectMonth=currentMonth;
        selectYear=currentYear;
        System.out.println("======currentYear="+currentYear+"====currentMonth="+currentMonth);
        setYearAndMonth(currentYear,currentMonth);
        GridView gridView=new GridView(this.getContext());
        customCalenderAdapter=new CustomCalenderAdapter();
        gridView.setAdapter(customCalenderAdapter);
    }

    public void getAttrs(AttributeSet attrs){
        yearMonthBgColor=attrs.getAttributeResourceValue(R.styleable.CustomCalenderView_ccv_yearMonthBgColor,R.color.white);
    }


    /**
     * 设置和年份月份
     */
    public void setYearAndMonth(int year,int month){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH,month-1);//默认1月为0月

        //获取当前月份的天数
        selectMonthDayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //当前月的1号是周几
        firstweek = calendar.get(Calendar.DAY_OF_WEEK)-1;
        //获取当前月所占用的行数
        lineNum=(selectMonthDayCount-(7-firstweek+1))/7+((selectMonthDayCount-(7-firstweek+1))%7==0?0:1);

    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        bgPaint=new Paint();
        yearMonthPaint=new Paint();
        drawYearMonthTitle(canvas);
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
        yearMonthPaint=new Paint();
        yearMonthPaint.setColor(yearMonthTextColor);
        yearMonthPaint.setTextSize(yearMonthTextSize);
        canvas.drawText(getTitle(selectYear,selectMonth),0,0,yearMonthPaint);

    }


    /**
     * 获取标题
     */
    public String getTitle(int year,int month){
        return year+"年"+(month>10?month:"0"+month)+"月";
    }





}
