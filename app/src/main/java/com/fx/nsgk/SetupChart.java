package com.fx.nsgk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class SetupChart {
    private final DatabaseHelper dbHelper;
    private final LineChart chart;
    private final Context context;

    public SetupChart(Context context, LineChart chart, DatabaseHelper dbHelper) {
        this.context = context;
        this.chart = chart;
        this.dbHelper = dbHelper;
    }




    //吊臂角度
    public void setupChart_angle(int angle, int workingConditionId) {
        if (angle > 90) {
            Toast.makeText(context, "角度应该小于90度", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<Double> results = dbHelper.getAngularWeight(angle, workingConditionId);
        Log.d("res", "setupChart_distances: "+ results);
        // 将 ArrayList 中的每个 Double 元素转换为 float 并填充到数组
        List<Entry> entries = new ArrayList<>();
        float[] weights = new float[results.size()];
        for (int i = 0; i < results.size(); i++) {
            weights[i] = results.get(i).floatValue();
        }

//        float[] distances = new float[]{7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 24.5F};
        List<Float> distances = new ArrayList<>();
        for (float i = 7.0f; i <= 24.5f; i += 0.1f) {
            distances.add(Math.round(i * 10) / 10.0f);  // 四舍五入到一位小数
        }

        for (int i = 0; i < distances.size(); i++) {
            if (weights[i] != 0) {  // 不添加非0权重的数据点
                entries.add(new Entry(distances.get(i), weights[i]));
            }
        }
        Log.d("entries", " "+ entries);

        XYset(chart,  entries);
    }

    public double setupChart_weight(int angle, double distances, int working) {
        if (distances > 24.5 || distances <7) {
            Toast.makeText(context, "长度在7和24.5之间", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if (angle > 90) {
            Toast.makeText(context, "角度应该小于90度", Toast.LENGTH_SHORT).show();
            return 0;
        }
        double working_weight = dbHelper.Anglelengthliftingweight(angle,distances,working);

        return working_weight;
    }



//吊臂长度
    public void setupChart_distances(double dist, int workingConditionId) {
        Log.d("111111111111111111", "setupChart_distances: "+ dist +"  "+ workingConditionId);

        if (dist > 24.5 || dist <7) {
            Toast.makeText(context, "长度在7和24.5之间", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<Double> results = dbHelper.getDistancesWeight(dist, workingConditionId);
        Log.d("res", "setupChart_distances: "+ results);
        // 将 ArrayList 中的每个 Double 元素转换为 float 并填充到数组
        List<Entry> entries = new ArrayList<>();
        float[] weights = new float[results.size()];
        Log.d("111111111111111111", "setupChart_distances: "+results.size());
        for (int i = 0; i < results.size(); i++) {
            weights[i] = results.get(i).floatValue();
        }

        float[] angles = new float[91];  // 创建包含91个元素的数组（从0到90，包括90）
        for (int i = 0; i < angles.length; i++) {
            angles[i] = i;  // 将每个索引值赋给数组元素
        }

        for (int i = 0; i < angles.length; i++) {
            if (weights[i] != 0) {  // 不添加非0权重的数据点
                entries.add(new Entry(angles[i], weights[i]));
            }
        }
        Log.d("entries", " "+ entries);
        XYset(chart, entries);
    }


    static void XYset(LineChart chart1, List<Entry> entries ){ // X 轴设置
        LineDataSet dataSet = new LineDataSet(entries, "工况表");
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLACK);
        // 不显示数据点上的值
        dataSet.setDrawValues(false);

        chart1.setScaleEnabled(false); // 禁止缩放

        LineData lineData = new LineData(dataSet);
        chart1.setData(lineData);

        XAxis xAxis = chart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1); // 每隔 1 单位显示一个刻度
//        xAxis.setGranularityEnabled(true); // 启用粒度控制
//        xAxis.setAxisLineColor(Color.BLACK);
//        xAxis.setAxisMinimum(7); // 设置 X 轴的最小值
//        xAxis.setAxisMaximum(25); // 设置 X 轴的最大值
//        xAxis.setLabelCount(19, true); // 强制显示所有标签

        // 隐藏右侧的 Y 轴
        YAxis rightAxis = chart1.getAxisRight();
        rightAxis.setAxisLineColor(Color.BLACK);
        rightAxis.setEnabled(false); // 不显示右侧 Y 轴

        // 设置描述
        Description description = new Description();
        description.setText(" ");
        description.setTextColor(Color.RED); // 设置文本颜色为蓝色
        description.setTextSize(10);
        description.setPosition(0, 0);      // 指定位置
        chart1.setDescription(description);
        chart1.invalidate(); // 刷新图表

    }


    //工况信息
    @SuppressLint("SetTextI18n")
    public String workinfo(int workingConditionId, Workingconditiontype wkt){
        String rt = wkt.rotationDescription;
        String cw = wkt.counterweightDescription;
        String ot = wkt.outriggerDescription;
        String rc = wkt.counterweightReach;
        return "工况:              "+workingConditionId + "\n"+ rt +"\n"+cw+"\n"+ot+"\n"+rc;
    }

}


