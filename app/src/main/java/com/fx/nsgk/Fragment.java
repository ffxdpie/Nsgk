package com.fx.nsgk;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;


public class Fragment extends androidx.fragment.app.Fragment {


    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "angle";
    private static final String ARG_PARAM3 = "workingConditionId";
    private static final String ARG_PARAM4 = "dist";


    private String title;
    private EditText editTextNumber,editTextNumber1;
    private int dist;
    private int workingConditionId;
    private int var = -1;
    private int angle;
    private TextView edittext ,edittext1;
    private LineChart chart;


    public Fragment() {
        // Required empty public constructor
    }


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
        editTextNumber = view.findViewById(R.id.editTextNumber2);
        editTextNumber1 = view.findViewById(R.id.editTextNumber3);
        edittext = view.findViewById(R.id.textView6);
        edittext1 = view.findViewById(R.id.textView7);
        Button button = view.findViewById(R.id.button3);
        Button button1 = view.findViewById(R.id.button4);
        chart = view.findViewById(R.id.chart);
        setupChartIfNeeded();

        button.setOnClickListener(v -> {
            if (getContext() != null) {
                String textvar = editTextNumber.getText().toString(); // 获取EditText中的文本
                if (! textvar.isEmpty()) { // 检查文本是否为空
                    try {
                        // 尝试将文本转换为整数
                        var = Integer.parseInt(textvar);
                    } catch (NumberFormatException e) {
                        return; // 退出方法，不进行图表更新
                    }
                }
                setupChartIfNeeded();
            }
        });

        button1.setOnClickListener(v -> {
            String inputvalue = editTextNumber1.getText().toString();
            switch (title) {
                case "角度和起重量":

                    break;
                case "吊臂长度和起重量":

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
                    editTextNumber.setHint("长度");
                    setupChart.setupChart_distances(var, workingConditionId);
                    break;
                case "吊臂长度和起重量":
                    if (var == -1){
                        var = angle;
                    }

                    edittext.setText("角度0-90");
                    editTextNumber.setHint("角度");

                    setupChart.setupChart_angle(var, workingConditionId);
                    break;
                default:
                    // 你可以在这里处理任何默认情况，或者什么也不做
                    break;
            }
        }

    }
}