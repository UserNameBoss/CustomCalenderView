package com.example.lenovo.customcalender;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
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




    //日期的字体大小
    private float dayTextSize=40f;
    //日期的行间距
    private float dayRowSize=80f;
    //日期的字体颜色
    private int dayTextColor=0;
    //当前日期的背景颜色
    private int currentDayBgColor=0;

    //画笔相关
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

    @Override
    protected void onDraw(Canvas canvas) {


        dayPaint=new Paint();
        currentDayPaint=new Paint();
        dayPaint.setTextSize(dayTextSize);
        dayPaint.setColor(dayTextColor);
        currentDayPaint.setColor(currentDayBgColor);
        dayPaint.setTextAlign(Paint.Align.CENTER);
        drawDay(canvas);
    }

    /**
     * 绘制日期
     */
    public void drawDay(Canvas canvas){
        int weekOneWidth=getWidth()/7;
        int dayCount=1;
        for(int i=0;i<lineNum;i++) {
            for(int j=0;j<7;j++){
                if(i==0){
                    if(j>=firstweek){
                        if(selectYear==currentYear&&selectMonth==currentMonth&&currentDay==dayCount){
                            //绘制今天日期的背景
                            canvas.drawCircle(j*weekOneWidth+weekOneWidth/2,(dayRowSize+dayTextSize)*(i+1)-dayTextSize/2,dayTextSize+20,currentDayPaint);
                        }
                        canvas.drawText(dayCount+"",j*weekOneWidth+weekOneWidth/2,(dayRowSize+dayTextSize)*(i+1),dayPaint);

                        dayCount++;
                    }
                }else{
                    if(selectYear==currentYear&&selectMonth==currentMonth&&currentDay==dayCount){
                        //绘制今天日期的背景
                        canvas.drawCircle(j*weekOneWidth+weekOneWidth/2,(dayRowSize+dayTextSize)*(i+1)-dayTextSize/2,dayTextSize+20,currentDayPaint);
                    }
                    canvas.drawText(dayCount+"",j*weekOneWidth+weekOneWidth/2,(dayRowSize+dayTextSize)*(i+1),dayPaint);
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
    public String getTitle(){
        return selectYear+"年"+(selectMonth>=10?selectMonth:"0"+selectMonth)+"月";
    }


    public int getSelectMonth() {
        return selectMonth;
    }

    public int getSelectYear() {
        return selectYear;
    }
}
