package com.example.lenovo.customcalender;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import java.util.Calendar;

/**
 * Created by lenovo on 2018/6/29 029.
 */

public class CustomCalenderView extends View {

    //所选择的月份和年份
    private int selectMonth=-1;
    private int selectYear=2018;
    //所选择的月份有多少天
    private int selectMonthDayCount=30;
    //所选择的月份1号是周几
    private int firstweek=1;
    //所选择的月份要显示几行
    private int lineNum=5;
    //当前年份和当前月份
    private int currentYear,currentMonth,currentDay;


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


    //日期的字体大小
    private float dayTextSize=40f;
    //日期的行间距
    private float dayRowSize=80f;
    //日期的字体颜色
    private int dayTextColor=0;
    //当前日期的背景颜色
    private int currentDayBgColor=0;

    //画笔相关
    //绘制背景
    private Paint bgPaint;
    //绘制年月标题
    private Paint yearMonthPaint;
    //绘制星期几
    private Paint weekPaint;
    //绘制日期
    private Paint dayPaint;
    //绘制当前日期的背景
    private Paint currentDayPaint;
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

    public CustomCalenderView(Context context,TypedArray typedArray){
        super(context);
        getAttrs(typedArray);
        initView();
    }
    public CustomCalenderView(int selectYear,int selectMonth,Context context, TypedArray attrs) {
        super(context);
        this.selectYear=selectYear;
        this.selectMonth=selectMonth;
        getAttrs(attrs);
        initView();
    }

    public void initView(){

        Calendar calendar=Calendar.getInstance();
        currentYear=calendar.get(Calendar.YEAR);
        currentMonth=calendar.get(Calendar.MONTH)+1;
        currentDay=calendar.get(Calendar.DAY_OF_MONTH);
        if(selectMonth==-1) {
            selectMonth = currentMonth;
            selectYear = currentYear;
        }
        System.out.println("========selectYear="+selectYear+"==selectMonth="+selectMonth);
        setYearAndMonth(selectYear,selectMonth);
        GridView gridView=new GridView(this.getContext());
        customCalenderAdapter=new CustomCalenderAdapter();
        gridView.setAdapter(customCalenderAdapter);
        setWillNotDraw(false);
    }
    public void getAttrs(AttributeSet attrs){
        TypedArray typedArray =this.getContext().obtainStyledAttributes(attrs, R.styleable.CustomCalenderView);
        getAttrs(typedArray);
    }
    @SuppressLint("ResourceAsColor")
    public void getAttrs(TypedArray typedArray){

        yearMonthBgColor=typedArray.getColor(R.styleable.CustomCalenderView_ccv_yearMonthBgColor,R.color.white);
        yearMonthTextColor=typedArray.getColor(R.styleable.CustomCalenderView_ccv_yearMonthTextColor,R.color.white);
        weekTextColor=typedArray.getColor(R.styleable.CustomCalenderView_ccv_weekTextColor,R.color.black);
        dayTextColor=typedArray.getColor(R.styleable.CustomCalenderView_ccv_dayTextSize,R.color.black);
        currentDayBgColor=typedArray.getColor(R.styleable.CustomCalenderView_ccv_currentDayBgColor,R.color.colorAccent);
    }




    /**
     * 设置和年份月份
     */
    public void setYearAndMonth(int year,int month){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH,month-1);//默认1月为0月
        //当前月的1号是周几
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        firstweek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        //获取当前月份的天数
        selectMonthDayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        //获取当前月所占用的行数
        lineNum=(selectMonthDayCount-(7-firstweek))/7+((selectMonthDayCount-(7-firstweek))%7==0?0:1)+1;

    }


//    @Override
//    public void draw(Canvas canvas) {
//        super.draw(canvas);
//        System.out.println("========01======");
//
//    }

    @Override
    protected void onDraw(Canvas canvas) {
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

        dayPaint=new Paint();
        currentDayPaint=new Paint();
        dayPaint.setTextSize(dayTextSize);
        dayPaint.setColor(dayTextColor);
        currentDayPaint.setColor(currentDayBgColor);
        dayPaint.setTextAlign(Paint.Align.CENTER);
        drawDay(canvas);
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

    /**
     * 绘制日期
     */
    public void drawDay(Canvas canvas){
        float dayY=yearMonthTitleHeight+weekRowHeight;
        int weekOneWidth=getWidth()/7;
        int dayCount=1;
        for(int i=0;i<lineNum;i++) {
            for(int j=0;j<7;j++){
                if(i==0){
                    if(j>=firstweek){
                        if(currentDay==dayCount){
                            //绘制今天日期的背景
                            canvas.drawCircle(j*weekOneWidth+weekOneWidth/2,dayY+(dayRowSize+dayTextSize)*i-dayTextSize/2,dayTextSize+20,currentDayPaint);
                        }
                        canvas.drawText(dayCount+"",j*weekOneWidth+weekOneWidth/2,dayY+(dayRowSize+dayTextSize)*i,dayPaint);

                        dayCount++;
                    }
                }else{
                    if(currentDay==dayCount){
                        //绘制今天日期的背景
                        canvas.drawCircle(j*weekOneWidth+weekOneWidth/2,dayY+(dayRowSize+dayTextSize)*i-dayTextSize/2,dayTextSize+20,currentDayPaint);
                    }
                    canvas.drawText(dayCount+"",j*weekOneWidth+weekOneWidth/2,dayY+(dayRowSize+dayTextSize)*i,dayPaint);
                    dayCount++;
                }
                if(dayCount>selectMonthDayCount){
                    return;
                }
            }
        }
    }


    /**
     * 获取标题
     */
    public String getTitle(int year,int month){
        return year+"年"+(month>10?month:"0"+month)+"月";
    }


}
