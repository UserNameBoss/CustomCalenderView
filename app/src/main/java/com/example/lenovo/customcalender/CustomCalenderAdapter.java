package com.example.lenovo.customcalender;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2018/6/29 029.
 */

public class CustomCalenderAdapter extends BaseAdapter {


    //所选择的月份和年份
    private int selectMonth = 1;
    private int selectYear = 2018;
    //所选择的月份有多少天
    private int selectMonthDayCount = 30;
    //所选择的月份1号是周几
    private int firstweek = 1;
    //所选择的月份要显示几行
    private int lineNum = 5;
    //当前年份和当前月份
    private int currentYear, currentMonth;


    public CustomCalenderAdapter() {
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        selectMonth = currentMonth;
        selectYear = currentYear;
        setYearAndMonth(selectYear, selectMonth);
    }

    /**
     * 设置和年份月份
     */
    public void setYearAndMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        //当前月的1号是周几 0:是星期天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        firstweek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);//默认1月为0月

        //获取当前月份的天数
        selectMonthDayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        //获取当前月所占用的行数
        lineNum = (selectMonthDayCount - (7 - firstweek)) / 7 + ((selectMonthDayCount - (7 - firstweek)) % 7 == 0 ? 0 : 1)+1;
        System.out.println("====selectMonthDayCount="+selectMonthDayCount+"===firstweek="+firstweek+"========lineNum="+lineNum);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return lineNum * 7;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null) {
            DisplayMetrics dm2 =parent.getResources().getDisplayMetrics();

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_calender, parent, false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        if(position>firstweek-1&&position<selectMonthDayCount+firstweek){
            int content=position-firstweek+1;
            viewHolder.tvDay.setText((content>=10?content+"":"0"+content));
        }else{
            viewHolder.tvDay.setText("");
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_day)
        TextView tvDay;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
