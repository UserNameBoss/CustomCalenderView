package com.example.lenovo.customcalender;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
    //所选择的月份最后一天是周几
    private int lastWeek=0;
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

    //绘制点击的图标的直径
    private float cirRadius=60f;

    //画笔相关
    //绘制日期
    private Paint dayPaint;
    //绘制当前日期的背景
    private Paint currentDayPaint;
    private CustomCalenderAdapter customCalenderAdapter;
    private GestureDetector gestureDetector;
    private int clickRow=-1,clickColumn=-1;
    private OnClickEventListener onClickEventListener;

    public void setOnClickEventListener(OnClickEventListener onClickEventListener) {
        this.onClickEventListener = onClickEventListener;
    }

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

        gestureDetector=new GestureDetector(this.getContext(),new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                useClick(e.getX(),e.getY());
                return true;
            }
        });
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
        //最后一天是周几
        calendar.set(Calendar.DAY_OF_MONTH, selectMonthDayCount);
        lastWeek=calendar.get(Calendar.DAY_OF_WEEK) - 1;
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
                            //canvas.drawCircle(j*weekOneWidth+weekOneWidth/2,(dayRowSize+dayTextSize)*(i+1)-dayTextSize/2,dayTextSize+20,currentDayPaint);
                            if(clickColumn==-1) {
                                clickColumn = j;
                                clickRow = i;
                            }

                        }
                        canvas.drawText(dayCount+"",j*weekOneWidth+weekOneWidth/2,(dayRowSize+dayTextSize)*(i+1),dayPaint);

                        dayCount++;
                    }
                }else{
                    if(selectYear==currentYear&&selectMonth==currentMonth&&currentDay==dayCount){
                        if(clickColumn==-1) {
                            clickColumn = i;
                            clickRow = j;
                        }

                        //绘制今天日期的背景
                        //canvas.drawCircle(j*weekOneWidth+weekOneWidth/2,(dayRowSize+dayTextSize)*(i+1)-dayTextSize/2,dayTextSize+20,currentDayPaint);
                    }
                    canvas.drawText(dayCount+"",j*weekOneWidth+weekOneWidth/2,(dayRowSize+dayTextSize)*(i+1),dayPaint);
                    dayCount++;
                }
                if(dayCount>selectMonthDayCount){
                    break;
                }
            }

            if(dayCount>selectMonthDayCount){
                break;
            }
        }
        System.out.println("========clickColumn="+clickColumn+"===clickRow="+clickRow);
        if(clickColumn!=-1) {
            canvas.drawCircle(clickColumn * weekOneWidth + weekOneWidth / 2, (dayRowSize + dayTextSize) * (clickRow + 1) - dayTextSize / 2, cirRadius, currentDayPaint);
            canvas.drawText(dayCount+"",clickColumn*weekOneWidth+weekOneWidth/2,(dayRowSize+dayTextSize)*(clickRow+1),dayPaint);

        }
    }


    //处理点击
    public void useClick(float x,float y){
        System.out.println("========x="+x+"====y="+y);
        int column=(int)x/(getWidth()/7);
        int row= (int) (y/(dayRowSize+dayTextSize));
        if((row==clickRow&&column==clickColumn)||(row>=0&&row<lineNum)){
            return;
        }
        System.out.println("=====column="+column+"===row="+row);
        //计算要重新绘制的部分
        int l= (int) (((getWidth()/7*column+getWidth()/7*(column+1))/2-cirRadius)-1);
        int r=(int) (((getWidth()/7*column+getWidth()/7*(column+1))/2+cirRadius)+1);
        int t= (int) (((dayRowSize+dayTextSize)*row+(dayRowSize+dayTextSize)*(row+1))/2-cirRadius-1);
        int b=(int) (((dayRowSize+dayTextSize)*row+(dayRowSize+dayTextSize)*(row+2))/2+cirRadius+1);
        //之前的部分要清除
        int oldl = (int) (((getWidth() / 7 * clickColumn + getWidth() / 7 * (clickColumn + 1)) / 2 - cirRadius ) - 1);
        int oldr = (int) (((getWidth() / 7 * clickColumn + getWidth() / 7 * (clickColumn + 1)) / 2 + cirRadius) + 1);
        int oldt = (int) (((dayRowSize + dayTextSize) * clickRow + (dayRowSize + dayTextSize) * (clickRow + 1)) / 2 - cirRadius - 1);
        int oldb = (int) (((dayRowSize + dayTextSize) * clickRow + (dayRowSize + dayTextSize) * (clickRow + 2)) / 2 + cirRadius + 1);

        if(row>=0&&row<lineNum) {
            if (row == 0) {
                if (column >= firstweek) {
                    clickRow = row;
                    clickColumn = column;
                    invalidate(oldl,oldt,oldr,oldb);
                    invalidate(l, t, r, b);
//                    if(onClickEventListener!=null){
//
//                    }
                }
            } else {
                if (row == lineNum - 1) {
                    if (column <= lastWeek) {
                        clickRow = row;
                        clickColumn = column;
                        invalidate(oldl,oldt,oldr,oldb);
                        invalidate(l, t, r, b);
                    }
                } else {
                    clickRow = row;
                    clickColumn = column;
                    invalidate(oldl,oldt,oldr,oldb);
                    invalidate(l, t, r, b);
                }
            }
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
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
