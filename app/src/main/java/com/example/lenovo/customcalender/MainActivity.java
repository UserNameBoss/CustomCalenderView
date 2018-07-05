package com.example.lenovo.customcalender;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    private CustomCalenderAdapter customCalenderAdapter;
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView=findViewById(R.id.gri_calender);
        customCalenderAdapter=new CustomCalenderAdapter();
        gridView.setAdapter(customCalenderAdapter);
    }
}
