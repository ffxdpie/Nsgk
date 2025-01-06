package com.fx.nsgk.nsgk;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fx.nsgk.DatabaseHelper;
import com.fx.nsgk.R;
import com.github.mikephil.charting.charts.LineChart;


public class Fragment extends androidx.fragment.app.Fragment {


    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "angle";
    private static final String ARG_PARAM3 = "workingConditionId";
    private static final String ARG_PARAM4 = "dist";


    private String title;
    private EditText editTextNumber2,editTextNumber3;
    private double dist;
    private int workingConditionId;
    private double var = -1;
    private int angle;
    private TextView edittext ,edittext1;
    private LineChart chart;



    public static Fragment newInstance(String param1 , int param2 , int param3, int param4) {
        Fragment fragment = new Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        args.putInt(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
            workingConditionId = getArguments().getInt(ARG_PARAM2);
            angle= getArguments().getInt(ARG_PARAM3);
            dist = getArguments().getInt(ARG_PARAM4);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dis, container, false);
        editTextNumber2 = view.findViewById(R.id.editTextNumber2);
        editTextNumber3 = view.findViewById(R.id.editTextNumber3);
        edittext = view.findViewById(R.id.textView6);
        edittext1 = view.findViewById(R.id.textView7);
        Button button = view.findViewById(R.id.button3);
        Button button1 = view.findViewById(R.id.button4);
        chart = view.findViewById(R.id.chart);

        setupChartIfNeeded();


        button.setOnClickListener(v -> {
            if (getContext() != null) {
                String textvar = editTextNumber2.getText().toString(); // 获取EditText中的文本
                if (! textvar.isEmpty()) { // 检查文本是否为空
                    try {
                        // 尝试将文本转换为double
                        var = Math.round(Double.parseDouble(textvar)* 10) / 10.0;
                    } catch (NumberFormatException e) {
                        return; // 退出方法，不进行图表更新
                    }
                }
                setupChartIfNeeded();
            }
        });

        button1.setOnClickListener(v -> {
            button.performClick();
            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            SetupChart setupChart = new SetupChart(getContext(), chart, dbHelper);
            String weight;
            int ang = 0;
            double d = 7.0;

            switch (title) {
                case "角度和起重量":
                    if (editTextNumber2 != null && editTextNumber3 != null) {
                        double inputvalue3;
                        double inputvalue2;
                        try {
                            inputvalue2 = Double.parseDouble(editTextNumber2.getText().toString());
                            inputvalue3 = Double.parseDouble(editTextNumber3.getText().toString());
                            // 更多的逻辑
                        } catch (NumberFormatException e) {
                            Toast.makeText(getContext(), "请输入有效的数字", Toast.LENGTH_SHORT).show();
                            return;  // 阻止进一步执行
                        }
                        ang = (int) Math.round(inputvalue3);
                        d = inputvalue2;
                    }

                    weight = String.valueOf(setupChart.setupChart_weight(ang, d, workingConditionId));
                    edittext1.setText(weight);
                    break;
                case "吊臂长度和起重量":
                    if (editTextNumber2 != null && editTextNumber3 != null) {
                        double inputvalue2;
                        double inputvalue3;
                        try {
                            inputvalue2 = Double.parseDouble(editTextNumber2.getText().toString());
                            inputvalue3 = Double.parseDouble(editTextNumber3.getText().toString());
                            // 更多的逻辑
                        } catch (NumberFormatException e) {
                            Toast.makeText(getContext(), "请输入有效的数字", Toast.LENGTH_SHORT).show();
                            return;  // 阻止进一步执行
                        }
                        d =  Math.round(inputvalue3* 10) / 10.0;
                        ang = (int) inputvalue2;
                    }
                    weight = String.valueOf(setupChart.setupChart_weight(ang, d, workingConditionId));
                    edittext1.setText(weight);
                    break;
                default:
                    // 你可以在这里处理任何默认情况，或者什么也不做
                    break;
            }


        });
        Log.d("Fragment_dis", "Received args: title=" + title + ", angle=" + var + ", workingConditionId=" + workingConditionId);
        return view;
    }




    @SuppressLint("SetTextI18n")
    private void setupChartIfNeeded() {
        if (getContext() != null) {
            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            SetupChart setupChart = new SetupChart(getContext(), chart, dbHelper);

            switch (title) {
                case "角度和起重量":
                    if (var == -1){
                        var = dist;
                    }

                    edittext.setText("长度7-24.5");
                    editTextNumber2.setHint("输入吊臂长度");
                    editTextNumber3.setHint("输入角度");
                    setupChart.setupChart_distances(var, workingConditionId);
                    break;
                case "吊臂长度和起重量":
                    if (var == -1){
                        var = angle;
                    }

                    edittext.setText("角度0-90");
                    editTextNumber2.setHint("输入角度");
                    editTextNumber3.setHint("输入吊臂长度");

                    setupChart.setupChart_angle((int) var, workingConditionId);
                    break;
                default:
                    // 你可以在这里处理任何默认情况，或者什么也不做
                    break;
            }
        }

    }
}