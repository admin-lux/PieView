package com.rick.pieview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        PieView pieView = (PieView) findViewById(R.id.pie_view);
        pieView.setData(getData());

    }

    private ArrayList<PieData> getData() {
        ArrayList<PieData> list = new ArrayList();
        list.add(new PieData("张三", 20));
        list.add(new PieData("张三", 30));
        list.add(new PieData("张三", 20));
        list.add(new PieData("张三", 30));
        return list;

    }
}
